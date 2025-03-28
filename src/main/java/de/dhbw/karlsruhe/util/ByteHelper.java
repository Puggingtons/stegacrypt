package de.dhbw.karlsruhe.util;

public class ByteHelper {
    public static byte[] toPrimitive(Byte[] bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[i];
        }
        return result;
    }

    public static byte b(int i) {
        return (byte) i;
    }
}
