package de.dhbw.karlsruhe.steganography.basic;

import de.dhbw.karlsruhe.steganography.Steganography;

import java.awt.image.BufferedImage;

public class BasicSteganography extends Steganography {

    private final BasicWriter basicWriter;
    private final BasicDecoder basicDecoder;

    public BasicSteganography() {
        this.basicWriter = new BasicWriter();
        this.basicDecoder = new BasicDecoder();
    }

    @Override
    public boolean canEncode(byte[] input, BufferedImage image) {
        return (headerSize() + encodedSize(input)) < maxSize(image);
    }

    @Override
    protected BasicWriter getWriter() {
        return basicWriter;
    }

    @Override
    protected BasicDecoder getDecoder() {
        return basicDecoder;
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
