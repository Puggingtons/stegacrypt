package de.dhbw.karlsruhe.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class FileHelper {

    public static final int EXTENSION_BYTES = 16;

    public static byte[] readFileWithExtension(File file) throws IOException {
        byte[] fileExtensionBytes = getExtension(file).getBytes();
        byte[] fileBytes = Files.readAllBytes(file.toPath());

        byte[] bytes = new byte[EXTENSION_BYTES + fileBytes.length];

        System.arraycopy(fileExtensionBytes, 0, bytes, 0, fileExtensionBytes.length);
        System.arraycopy(fileBytes, 0, bytes, 16, fileBytes.length);

        return bytes;
    }

    public static String getExtension(File file) {
        if (file == null) {
            return "";
        }
        String name = file.getName();
        int i = name.lastIndexOf('.');
        return i > 0 ? name.substring(i + 1) : "";
    }

    public static String getExtension(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < EXTENSION_BYTES; i++) {
            if (bytes[i] == 0) {
                break;
            }

            sb.append((char) bytes[i]);
        }

        return sb.toString();
    }

    public static byte[] getData(byte[] bytes) {
        if (bytes == null || bytes.length < 16) {
            return new byte[0];
        }

        return Arrays.copyOfRange(bytes, EXTENSION_BYTES, bytes.length);
    }
}
