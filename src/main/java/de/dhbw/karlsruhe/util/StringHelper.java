package de.dhbw.karlsruhe.util;

public class StringHelper {
    public static String toFixedBinary(int number, int digits) {
        return leftPad(Integer.toBinaryString(number), digits, '0');
    }

    public static String leftPad(String text, int len, char value) {
        StringBuilder sb = new StringBuilder();
        if (text.length() < len) {
            sb.append(String.valueOf(value).repeat(len - text.length()));
            sb.append(text);
            return sb.toString();
        }
        return text;
    }

    public static String rightPad(String text, int len, char value) {
        StringBuilder sb = new StringBuilder();
        if (text.length() < len) {
            sb.append(text);
            sb.append(String.valueOf(value).repeat(len - text.length()));
            return sb.toString();
        }
        return text;
    }

}
