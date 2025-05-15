package de.dhbw.karlsruhe.cryptography;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

public class AESCryptography extends Cryptography {

    private final byte[] initializationVector = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

    public static SecretKeySpec generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[16];
        random.nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public AESCryptography() {
    }

    @Override
    public String toString() {
        return "AES";
    }

    @Override
    public byte[] encrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(initializationVector));

        return cipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(initializationVector));

        return cipher.doFinal(data);
    }

    private Cipher getCipher() throws Exception {
        return Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }
}
