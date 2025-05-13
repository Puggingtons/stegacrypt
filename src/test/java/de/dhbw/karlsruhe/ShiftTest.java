package de.dhbw.karlsruhe;

import de.dhbw.karlsruhe.util.StringHelper;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static de.dhbw.karlsruhe.util.IntHelper.countSetBits;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShiftTest {
    @Test
    public void testShift() {
        int rMask = 0x000001;

        for (int i = 0; i < 8; i++) {
            System.out.println(StringHelper.toFixedBinary(~(rMask << i), 32));
        }
    }

    @Test
    public void testCountSetBits() {
        assertEquals(1, countSetBits(0x00000001));
        assertEquals(4, countSetBits(0x00001111));
        assertEquals(21, countSetBits(0xFEFEFE));
        assertEquals(2, countSetBits(0b00000000000000000000000100000001));
        assertEquals(13, countSetBits(0b11111111000000110000001100000001));
    }

    @Test
    public void testCountSetBits2() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                assertEquals(0, countSetBits(image.getRGB(x, y)));
            }
        }
    }
}
