package de.dhbw.karlsruhe.steganography.stegacrypt;

import de.dhbw.karlsruhe.steganography.Encoder;
import de.dhbw.karlsruhe.util.IntHelper;

import java.awt.image.BufferedImage;
import java.util.Random;

import static de.dhbw.karlsruhe.util.BufferedImageHelper.deepCopy;

public class StegaCryptEncoder implements Encoder {

    private final int bitDepth;

    private BufferedImage image;
    private int currentX;
    private int currentY;

    private Random random;

    public StegaCryptEncoder(int bitDepth, long seed) {
        this(bitDepth);
        this.random = new Random(seed);
    }

    public StegaCryptEncoder(int bitDepth) {
        this.bitDepth = bitDepth;

        this.random = new Random();

        currentX = 0;
        currentY = 0;
    }

    @Override
    public BufferedImage writeData(BufferedImage image, byte[] data) {
        System.out.println(image.getColorModel());
        this.image = deepCopy(image);

        addNoise(bitDepth);
        writeNumberOfBytes(data);
        writeDataBytes(data);
        System.out.println();

        return this.image;
    }

    private void addNoise(int bits) {
        int rMask = 0x010000;
        int gMask = 0x000100;
        int bMask = 0x000001;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixelColor = image.getRGB(x, y);

                for (int i = 0; i < bits; i++) {
                    pixelColor = pixelColor & ~(rMask << i);
                    if (random.nextBoolean()) {
                        pixelColor = pixelColor | (rMask << i);
                    }
                    pixelColor = pixelColor & ~(gMask << i);
                    if (random.nextBoolean()) {
                        pixelColor = pixelColor | (gMask << i);
                    }
                    pixelColor = pixelColor & ~(bMask << i);
                    if (random.nextBoolean()) {
                        pixelColor = pixelColor | (bMask << i);
                    }
                }


                image.setRGB(x, y, pixelColor);
            }
        }
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
        // one is encoded through even parity
        int currentColor = image.getRGB(currentX, currentY);
        int currentParity = IntHelper.countSetBits(currentColor);
        int bitMask = currentBitMask();

        // parity is odd
        if (currentParity % 2 == 1) {
            // bit at mask is one
            if ((currentColor & bitMask) != 0) {
                image.setRGB(currentX, currentY, currentColor & ~bitMask);
            } else {
                image.setRGB(currentX, currentY, currentColor | bitMask);
            }
        }

        next();
    }

    private void writeZero() {
        // zero is encoded through odd parity
        int currentColor = image.getRGB(currentX, currentY);
        int currentParity = IntHelper.countSetBits(currentColor);
        int bitMask = currentBitMask();

        // parity is even
        if (currentParity % 2 == 0) {

            // bit at mask is zero
            if ((currentColor & bitMask) == 0) {
                image.setRGB(currentX, currentY, currentColor | bitMask);
            } else {
                // make bit at mask zero
                image.setRGB(currentX, currentY, currentColor & ~bitMask);
            }
        }

        next();
    }

    private int currentBitMask() {
        // randomly choose one bit in the pixel in the bitDepth range
        int randomBit = 0b1 << random.nextInt(bitDepth);

        // randomly choose one of the colors in the pixel
        return ((randomBit) << (random.nextInt(3) * 8));
    }

    /**
     * This method iterates over the image.
     * First each pixel in a row (width)
     * Then each row in the image (height)
     */
    private void next() {
        currentX++;

        if (currentX >= image.getWidth()) {
            currentX = 0;
            currentY++;
        }

        if (currentY >= image.getHeight()) {
            throw new ArrayIndexOutOfBoundsException("out of bounds");
        }
    }
}
