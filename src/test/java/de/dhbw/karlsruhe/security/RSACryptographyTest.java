package de.dhbw.karlsruhe.security;

import de.dhbw.karlsruhe.cryptography.RSACryptography;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RSACryptographyTest {
    @Test
    public void itEncryptsAndDecrypts() throws Exception {
        KeyPair keys = RSACryptography.generateKeyPair();
        byte[] bytes = "Hello World!Hello World!".getBytes();

        RSACryptography cryptography = new RSACryptography();

        byte[] encrypted = cryptography.encrypt(bytes, keys.getPublic());
        byte[] decrypted = cryptography.decrypt(encrypted, keys.getPrivate());

        assertArrayEquals(bytes, decrypted);
    }
}
