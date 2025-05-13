package de.dhbw.karlsruhe.steganography.stegacrypt;

import de.dhbw.karlsruhe.steganography.Decoder;
import de.dhbw.karlsruhe.steganography.Encoder;
import de.dhbw.karlsruhe.steganography.Steganography;

import java.awt.image.BufferedImage;

public class StegaCryptSteganography extends Steganography {

    private final StegaCryptDecoder decoder;
    private final StegaCryptEncoder encoder;

    private final int bitDepth;

    public StegaCryptSteganography() {

        bitDepth = 2;

        this.decoder = new StegaCryptDecoder();
        this.encoder = new StegaCryptEncoder(bitDepth);
    }

    @Override
    public String toString() {
        return "StegaCrypt Steganography";
    }

    @Override
    public boolean canEncode(byte[] input, BufferedImage image) {
        return (headerSize() + encodedSize(input)) < maxSize(image);
    }

    @Override
    protected Encoder getWriter() {
        return encoder;
    }

    @Override
    protected Decoder getDecoder() {
        return decoder;
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
        return image.getWidth() * image.getHeight();
    }
}
