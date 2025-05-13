package de.dhbw.karlsruhe.security;

import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class RSAKeyHandler {
    public static void saveKeyToFile(Key privateKey, File file) throws IOException {
        JcaPEMWriter writer = new JcaPEMWriter(new FileWriter(file));
        writer.writeObject(privateKey);
        writer.close();
    }

    public static PublicKey loadPublicKeyFromFile(File file) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");

        try (FileReader keyReader = new FileReader(file); PemReader pemReader = new PemReader(keyReader)) {
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
            PublicKey publicKey = factory.generatePublic(pubKeySpec);
            return publicKey;
        }
    }

    public static PrivateKey loadPrivateKeyFromFile(File file) throws IOException {
        return loadPrivateKeyFromFile(file, "");
    }

    public static PrivateKey loadPrivateKeyFromFile(File file, String password) throws IOException {
        return loadKeyPairFromFile(file, password).getPrivate();
    }

    public static KeyPair loadKeyPairFromFile(File file, String password) throws IOException {
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

        return kp;
    }
}
