package de.dhbw.karlsruhe.cryptography;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import java.security.KeyPair;
import java.security.Security;

public class RSACryptographyTest {
    @BeforeAll
    public static void setUp() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    public void testEncodeAndDecode() {
        RSACryptography cryptography = new RSACryptography();
        byte[] bytes = "Hello World!".getBytes();

        try {
            KeyPair keyPair = RSACryptography.generateKeyPair();

            // System.out.println(ByteHelper.bytesToString(keyPair.getPrivate().getEncoded()));
            System.out.println(keyPair.getPrivate().getFormat());

            byte[] encrypted = cryptography.encrypt(bytes, keyPair.getPublic());
            byte[] decrypted = cryptography.decrypt(encrypted, keyPair.getPrivate());

            Assertions.assertArrayEquals(bytes, decrypted);

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testEncodeAndDecodeWithDifferentKeys() {
        RSACryptography cryptography = new RSACryptography();
        byte[] bytes = "Hello World!".getBytes();

        Assertions.assertThrows(BadPaddingException.class, () -> {
            KeyPair keyPair1 = RSACryptography.generateKeyPair();
            KeyPair keyPair2 = RSACryptography.generateKeyPair();

            byte[] encrypted = cryptography.encrypt(bytes, keyPair1.getPublic());
            byte[] decrypted = cryptography.decrypt(encrypted, keyPair2.getPrivate());
        });
    }
}
