package de.dhbw.karlsruhe.cryptography;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSACryptography extends Cryptography {

    private static final String ALGORITHM = "RSA";
    private static final int DEFAULT_KEY_SIZE = 2048;

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return generateKeyPair(DEFAULT_KEY_SIZE);
    }

    public static KeyPair generateKeyPair(int size) throws NoSuchAlgorithmException {
        KeyPairGenerator kg = KeyPairGenerator.getInstance(ALGORITHM);
        kg.initialize(size);

        return kg.generateKeyPair();
    }

    public RSACryptography() {
    }

    @Override
    public String toString() {
        return ALGORITHM;
    }

    @Override
    public byte[] encrypt(byte[] data, Key key) throws Exception {
        Cipher rsaCipher = getCipher();
        rsaCipher.init(Cipher.ENCRYPT_MODE, key);

        return rsaCipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data, Key key) throws Exception {
        Cipher rsaCipher = getCipher();
        rsaCipher.init(Cipher.DECRYPT_MODE, key);

        return rsaCipher.doFinal(data);
    }

    private Cipher getCipher() throws Exception {
        return Cipher.getInstance("RSA");
        //     RSA/ECB/OAEPWithSHA-256AndMGF1Padding
    }
}
