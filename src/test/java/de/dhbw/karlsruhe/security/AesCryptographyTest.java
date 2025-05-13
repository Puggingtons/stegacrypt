package de.dhbw.karlsruhe.security;

import de.dhbw.karlsruhe.cryptography.AESCryptography;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AesCryptographyTest {
    @Test
    public void itEncryptsAndDecrypts() throws Exception {
        byte[] bytes = "Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!".getBytes();

        Key key = AESCryptography.generateKey();

        AESCryptography cryptography = new AESCryptography();

        byte[] encrypted = cryptography.encrypt(bytes, key);
        byte[] decrypted = cryptography.decrypt(encrypted, key);

        assertArrayEquals(bytes, decrypted);
    }
}
