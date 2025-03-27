package de.dhbw.karlsruhe.steganography;

import java.awt.image.BufferedImage;

public class ImageWriter {
    private final BufferedImage image;

    private int currentX;
    private int currentY;
    private int currentColorOffset;

    public ImageWriter(BufferedImage image) {
        this.image = image;
        currentX = 0;
        currentY = 0;
        currentColorOffset = 0;
    }

    public void writeData(byte[] data) {
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

    public void writeOne() {
        image.setRGB(currentX, currentY, image.getRGB(currentX, currentY) | currentBitMask());
        next();
    }

    public void writeZero() {
        image.setRGB(currentX, currentY, image.getRGB(currentX, currentY) & ~currentBitMask());
        next();
    }

    public BufferedImage getImage() {
        return image;
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
