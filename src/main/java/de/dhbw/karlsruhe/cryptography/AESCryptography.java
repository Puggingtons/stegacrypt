package de.dhbw.karlsruhe.cryptography;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;

public class AESCryptography extends Cryptography {

    KeyPair pair;

    public static SecretKeySpec generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[16];
        random.nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public AESCryptography() {
        try {
            this.pair = RSACryptography.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "AES";
    }

    @Override
    public byte[] encrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    public byte[] encryptData(byte[] dataToEncrypt) throws Exception {
        // Generate a symmetric key Using random AES algorithm
        SecretKeySpec symkey = generateKey();

        // Encrypt the data with the symmetric key
        Cipher aescipher = getCipher();
        aescipher.init(Cipher.ENCRYPT_MODE, symkey);
        byte[] encryptedData = aescipher.doFinal(dataToEncrypt);

        // Encrypt the symmetric key with RSA
        PublicKey publicKey = pair.getPublic();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedSymKey = cipher.doFinal(symkey.getEncoded());
        // Use a byte array to join everything
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(encryptedSymKey);
            baos.write(encryptedData);
            return baos.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }

    public byte[] decryptData(byte[] encryptedData) throws Exception {
        if (encryptedData == null || encryptedData.length < 256) {
            System.out.println("returning null");
            return null;
        }

        // read key and data separately
        final int SYMMECTRIC_KEY_LENGTH = 256; // this represents the key size after being encrypted
        byte[] symmectricKeyByes = new byte[SYMMECTRIC_KEY_LENGTH];
        for (int i = 0; i < SYMMECTRIC_KEY_LENGTH; i++) {
            symmectricKeyByes[i] = encryptedData[i];
        }

        byte[] dataToDecrypt = new byte[encryptedData.length - SYMMECTRIC_KEY_LENGTH];
        for (int i = SYMMECTRIC_KEY_LENGTH; i < encryptedData.length; i++) {
            dataToDecrypt[i - SYMMECTRIC_KEY_LENGTH] = encryptedData[i];
        }

        // Decrypte the encrypted symmetric key with RSA
        PrivateKey privateKey = pair.getPrivate();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKey = cipher.doFinal(symmectricKeyByes);
        SecretKeySpec symkey = new SecretKeySpec(decryptedKey, "AES");

        // Decrypte the data with the symmetric key
        Cipher aescipher = getCipher();
        aescipher.init(Cipher.DECRYPT_MODE, symkey);

        return aescipher.doFinal(dataToDecrypt);

    }

    private Cipher getCipher() throws Exception {
        return Cipher.getInstance("AES");
        // Cipher.getInstance("AES/CBC/PKCS5Padding")
    }
}
