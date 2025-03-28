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
        return input.length * 8 < image.getWidth() * image.getHeight();
    }

    @Override
    protected ImageWriter getImageWriter() {
        return imageWriter;
    }

    @Override
    protected ImageDecoder getImageDecoder() {
        return imageDecoder;
    }
}
