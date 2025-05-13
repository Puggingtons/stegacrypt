package de.dhbw.karlsruhe.steganography.stegacrypt;

import de.dhbw.karlsruhe.steganography.Decoder;
import de.dhbw.karlsruhe.util.IntHelper;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static de.dhbw.karlsruhe.util.ByteHelper.toPrimitive;

public class StegaCryptDecoder implements Decoder {

    private final List<Byte> bytes;

    private byte currentByte;
    private int bitCount;

    private int numberOfBytes;
    private int numberOfBytesBitCount;

    public StegaCryptDecoder() {
        this.bytes = new ArrayList<>();
        this.currentByte = 0;
        this.bitCount = 0;

        this.numberOfBytes = 0;
        this.numberOfBytesBitCount = 0;
    }

    @Override
    public byte[] decode(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getRGB(x, y) & 0xffffff;


                if ((IntHelper.countSetBits(color) % 2) == 0) {
                    pushOne();
                } else {
                    pushZero();
                }

                if (numberOfBytesBitCount == 32 && numberOfBytes == bytes.size()) {
                    return toPrimitive(bytes.toArray(new Byte[0]));
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
