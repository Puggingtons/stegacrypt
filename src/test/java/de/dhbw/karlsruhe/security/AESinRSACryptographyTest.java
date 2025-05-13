package de.dhbw.karlsruhe.security;

import de.dhbw.karlsruhe.cryptography.AESInRSACryptography;
import de.dhbw.karlsruhe.cryptography.RSACryptography;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AESinRSACryptographyTest {
    @Test
    public void itEncryptsAndDecrypts() throws Exception {
        byte[] bytes = "Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!".getBytes();
        AESInRSACryptography cryptography = new AESInRSACryptography();
        KeyPair pair = RSACryptography.generateKeyPair();


        byte[] encrypted = cryptography.encrypt(bytes, pair.getPublic());
        byte[] decrypted = cryptography.decrypt(encrypted, pair.getPrivate());

        assertArrayEquals(bytes, decrypted);
    }
}
