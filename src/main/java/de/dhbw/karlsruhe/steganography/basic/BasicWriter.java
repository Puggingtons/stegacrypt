package de.dhbw.karlsruhe.steganography.basic;

import de.dhbw.karlsruhe.steganography.Writer;

import java.awt.image.BufferedImage;

public class BasicWriter implements Writer {
    private BufferedImage image;

    private int currentX;
    private int currentY;
    private int currentColorOffset;

    public BasicWriter() {
        currentX = 0;
        currentY = 0;
        currentColorOffset = 0;
    }

    public BufferedImage writeData(BufferedImage image, byte[] data) {
        this.image = image;
        writeNumberOfBytes(data);
        writeDataBytes(data);

        return this.image;
    }

    private void writeNumberOfBytes(byte[] data) {
        int numberOfBytes = data.length;

        for (int i = 0; i < 32; i++) {
            if (((numberOfBytes >> 31) & 1) == 1) {
                writeOne();
            } else {
                writeZero();
            }

            numberOfBytes = numberOfBytes << 1;
        }
    }

    private void writeDataBytes(byte[] data) {
        for (byte b : data) {
            for (int i = 0; i < 8; i++) {
                if ((b & 128) == 128) {
                    writeOne();
                } else {
                    writeZero();
                }

                b = (byte) (b << 1);
            }
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
