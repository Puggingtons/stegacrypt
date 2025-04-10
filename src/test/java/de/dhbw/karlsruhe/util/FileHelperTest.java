package de.dhbw.karlsruhe.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static de.dhbw.karlsruhe.util.FileHelper.readFileWithExtension;
import static org.junit.jupiter.api.Assertions.*;

public class FileHelperTest {

    @Test
    public void testGetExtension() {
        byte[] bytes = new byte[32];
        String extension = "test";
        System.arraycopy(extension.getBytes(), 0, bytes, 0, extension.getBytes().length);

        assertEquals(extension, FileHelper.getExtension(bytes));
    }

    @Test
    public void testGetExtensionTooLong() {
        byte[] bytes = new byte[32];
        String extension = "testtesttesttesttest";
        System.arraycopy(extension.getBytes(), 0, bytes, 0, extension.getBytes().length);

        assertNotEquals(extension, FileHelper.getExtension(bytes));
    }

    @Test
    public void testGetExtensionEmpty() {
        byte[] bytes = new byte[32];
        String extension = "";
        System.arraycopy(extension.getBytes(), 0, bytes, 0, extension.getBytes().length);

        assertEquals(extension, FileHelper.getExtension(bytes));
    }

    @Test
    public void testGetExtensionEmptyArray() {
        byte[] bytes = new byte[0];

        assertEquals("", FileHelper.getExtension(bytes));
    }

    @Test
    public void testFileReadCorrectExtension() {
        File file = new File("examples/test.txt");

        try {
            byte[] bytes = readFileWithExtension(file);

            assertEquals("txt", FileHelper.getExtension(bytes));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testFileReadCorrectData() {
        File file = new File("examples/test.txt");

        try {
            byte[] bytes = readFileWithExtension(file);

            assertArrayEquals(Files.readAllBytes(file.toPath()), FileHelper.getData(bytes));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }


}
