package de.dhbw.karlsruhe.steganography.basic;

import de.dhbw.karlsruhe.steganography.Encoder;

import java.awt.image.BufferedImage;

import static de.dhbw.karlsruhe.util.BufferedImageHelper.deepCopy;

// 1 byte         4 byte            # bytes
// <algorithm id> <number of bytes> <bytes of data>

public class BasicEncoder implements Encoder {

    public final byte ALGORITHM_ID = 1;

    private BufferedImage image;

    private int currentX;
    private int currentY;
    private int currentColorOffset;

    public BasicEncoder() {
        currentX = 0;
        currentY = 0;
        currentColorOffset = 0;
    }

    public BufferedImage writeData(BufferedImage image, byte[] data) {
        this.image = deepCopy(image);

        // writeAlgorithmId();

        writeNumberOfBytes(data);
        writeDataBytes(data);

        return this.image;
    }

    private void writeAlgorithmId() {
        writeByte(ALGORITHM_ID);
    }

    private void writeNumberOfBytes(byte[] data) {
        writeInt(data.length);
    }

    private void writeDataBytes(byte[] data) {
        for (byte b : data) {
            writeByte(b);
        }
    }

    private void writeInt(int i) {
        writeByte((byte) (i >> 24));
        writeByte((byte) (i >> 16));
        writeByte((byte) (i >> 8));
        writeByte((byte) i);
    }

    private void writeByte(byte b) {
        for (int i = 0; i < 8; i++) {
            if ((b & 128) == 128) {
                writeOne();
            } else {
                writeZero();
            }

            b = (byte) (b << 1);
        }
    }

    private void writeOne() {
        image.setRGB(currentX, currentY, image.getRGB(currentX, currentY) | currentBitMask());
        next();
    }

    private void writeZero() {
        image.setRGB(currentX, currentY, image.getRGB(currentX, currentY) & ~currentBitMask());
        next();
    }

    private int currentBitMask() {
        return (0b1 << (currentColorOffset * 8));
    }

    /**
     * This method iterates over the image.
     * First each color of a pixel
     * Then each pixel in a row (width)
     * Then each row in the image (height)
     */
    private void next() {
        currentColorOffset++;

        if (currentColorOffset >= 3) {
            currentColorOffset = 0;
            currentX++;
        }

        if (currentX >= image.getWidth()) {
            currentX = 0;
            currentY++;
        }

        if (currentY >= image.getHeight()) {
            throw new ArrayIndexOutOfBoundsException("out of bounds");
        }
    }
}
