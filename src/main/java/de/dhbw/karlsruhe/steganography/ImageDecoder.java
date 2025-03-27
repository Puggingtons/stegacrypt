package de.dhbw.karlsruhe.steganography;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageDecoder {
    private final BufferedImage image;
    private List<Byte> bytes;

    private byte currentByte;
    private int bitCount;

    public ImageDecoder(BufferedImage image) {
        this.image = image;
        this.bytes = new ArrayList<>();
        this.currentByte = 0;
        this.bitCount = 0;
    }

    public Byte[] decode() {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getRGB(x, y);

                for (int colorOffset = 0; colorOffset < 3; colorOffset++) {
                    if ((color & currentBitMask(colorOffset)) != 0) {
                        pushOne();
                    } else {
                        pushZero();
                    }
                }

            }
        }

        return bytes.toArray(new Byte[0]);
    }

    private void pushOne() {
        push(1);
    }

    private void pushZero() {
        push(0);
    }

    private void push(int v) {
        currentByte = (byte) ((currentByte << 1) + v);
        bitCount++;

        if (bitCount == 8) {
            bytes.add(currentByte);
            currentByte = 0;
            bitCount = 0;
        }
    }

    private int currentBitMask(int colorOffset) {
        return (0b1 << (colorOffset * 8));
    }
}
