package de.dhbw.karlsruhe.steganography.basic;

import de.dhbw.karlsruhe.steganography.Decoder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static de.dhbw.karlsruhe.util.ByteHelper.toPrimitive;

public class LSBDecoder implements Decoder {
    private final List<Byte> bytes;

    private byte currentByte;
    private int bitCount;

    private int numberOfBytes;
    private int numberOfBytesBitCount;

    public LSBDecoder() {
        this.bytes = new ArrayList<>();
        this.currentByte = 0;
        this.bitCount = 0;

        this.numberOfBytes = 0;
        this.numberOfBytesBitCount = 0;
    }

    public byte[] decode(BufferedImage image) {
        this.bytes.clear();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getRGB(x, y);

                for (int colorOffset = 0; colorOffset < 3; colorOffset++) {
                    if ((color & currentBitMask(colorOffset)) != 0) {
                        pushOne();
                    } else {
                        pushZero();
                    }

                    if (numberOfBytesBitCount == 32 && numberOfBytes == bytes.size()) {
                        return toPrimitive(bytes.toArray(new Byte[0]));
                    }
                }
            }
        }

        return toPrimitive(bytes.toArray(new Byte[0]));
    }

    private void pushOne() {
        push(1);
    }

    private void pushZero() {
        push(0);
    }

    private void push(int v) {
        if (numberOfBytesBitCount != 32) {
            numberOfBytes = (numberOfBytes << 1) + v;
            numberOfBytesBitCount++;
            return;
        }

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
