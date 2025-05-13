package de.dhbw.karlsruhe.util;

public class IntHelper {
    // public static int countSetBits(int n) {
    //     int count = 0;
    //     while (n > 0) {
    //         n &= (n - 1);
    //         count++;
    //     }
    //     return count;
    // }

    public static int countSetBits(int n) {
        int count = 0;

        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n >>>= 1;
        }

        return count;
    }
}
