package de.dhbw.karlsruhe.security;

import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;

public class PrivateKeyHandler {
    public static void saveKeyToFile(PrivateKey privateKey, File file) throws IOException {
        JcaPEMWriter writer = new JcaPEMWriter(new FileWriter(file));
        writer.writeObject(privateKey);
        writer.close();
    }

    public static PrivateKey loadKeyFromFile(File file) throws IOException {
        return loadKeyFromFile(file, "");
    }

    public static PrivateKey loadKeyFromFile(File file, String password) throws IOException {
        PEMParser pemParser = new PEMParser(new FileReader(file));
        Object object = pemParser.readObject();

        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        KeyPair kp;

        if (object instanceof PEMEncryptedKeyPair) {
            // Encrypted key - we will use provided password
            PEMEncryptedKeyPair ckp = (PEMEncryptedKeyPair) object;
            PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(password.toCharArray());
            kp = converter.getKeyPair(ckp.decryptKeyPair(decProv));
        } else {
            // Unencrypted key - no password needed
            PEMKeyPair ukp = (PEMKeyPair) object;
            kp = converter.getKeyPair(ukp);
        }

        return kp.getPrivate();
    }
}
