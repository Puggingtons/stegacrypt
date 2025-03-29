package de.dhbw.karlsruhe.steganography;

import java.awt.image.BufferedImage;

public class BasicSteganography extends Steganography {

    private final ImageWriter imageWriter;
    private final ImageDecoder imageDecoder;

    public BasicSteganography() {
        this.imageWriter = new ImageWriter();
        this.imageDecoder = new ImageDecoder();
    }

    @Override
    public boolean canEncode(byte[] input, BufferedImage image) {
        return (headerSize() + encodedSize(input)) < maxSize(image);
    }

    @Override
    protected ImageWriter getWriter() {
        return imageWriter;
    }

    @Override
    protected ImageDecoder getDecoder() {
        return imageDecoder;
    }

    private int headerSize() {
        // number of bits in an integer
        return 32;
    }

    private int encodedSize(byte[] input) {
        // number of bits in the input
        return input.length * 8;
    }

    private int maxSize(BufferedImage image) {
        // image size * 3 (for each color used)
        return image.getWidth() * image.getHeight() * 3;
    }
}
