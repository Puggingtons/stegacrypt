package de.dhbw.karlsruhe.security;

import de.dhbw.karlsruhe.cryptography.RSACryptography;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.Security;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrivateKeyHandlerTest {

    @BeforeAll
    public static void setUp() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    public void itSavesAndLoadsPrivateKey(@TempDir Path tmpDir) {
        File tmpFile = tmpDir.resolve("test.key").toFile();

        try {
            PrivateKey pk = RSACryptography.generateKeyPair().getPrivate();
            PrivateKeyHandler.saveKeyToFile(pk, tmpFile);

            System.out.println(Files.readString(tmpFile.toPath()));

            PrivateKey loadedPk = PrivateKeyHandler.loadKeyFromFile(tmpFile);

            assertEquals(pk, loadedPk);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
