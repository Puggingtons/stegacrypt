package de.dhbw.karlsruhe.cryptography;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AESInRSACryptography extends Cryptography {

    // https://stackoverflow.com/questions/57161198/encrypt-and-decrypt-data-in-java-with-aes-rsa

    private final AESCryptography aesCryptography;
    private final RSACryptography rsaCryptography;

    private final int SYMMETRIC_KEY_LENGTH = 256;

    public AESInRSACryptography() {
        this.aesCryptography = new AESCryptography();
        this.rsaCryptography = new RSACryptography();
    }

    @Override
    public String toString() {
        return "AES in RSA";
    }

    @Override
    public byte[] encrypt(byte[] data, Key publicKey) throws Exception {
        Key aesKey = AESCryptography.generateKey();

        byte[] rsaEncryptedAESKey = rsaCryptography.encrypt(aesKey.getEncoded(), publicKey);
        byte[] aesEncrypted = aesCryptography.encrypt(data, aesKey);

        byte[] result = new byte[aesEncrypted.length + rsaEncryptedAESKey.length];
        System.arraycopy(rsaEncryptedAESKey, 0, result, 0, rsaEncryptedAESKey.length);
        System.arraycopy(aesEncrypted, 0, result, rsaEncryptedAESKey.length, aesEncrypted.length);

        return result;

        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // try {
        //     baos.write(rsaEncryptedAESKey);
        //     baos.write(aesEncrypted);
        //     return baos.toByteArray();
        // } catch (IOException ex) {
        //     return null;
        // }
    }

    @Override
    public byte[] decrypt(byte[] data, Key privateKey) throws Exception {
        byte[] symmetricKeyBytes = new byte[SYMMETRIC_KEY_LENGTH];
        byte[] dataToDecrypt = new byte[data.length - SYMMETRIC_KEY_LENGTH];

        System.arraycopy(data, 0, symmetricKeyBytes, 0, SYMMETRIC_KEY_LENGTH);
        System.arraycopy(data, SYMMETRIC_KEY_LENGTH, dataToDecrypt, 0, data.length - SYMMETRIC_KEY_LENGTH);

        SecretKeySpec aesKey = new SecretKeySpec(rsaCryptography.decrypt(symmetricKeyBytes, privateKey), "AES");
        return aesCryptography.decrypt(dataToDecrypt, aesKey);
    }
}
