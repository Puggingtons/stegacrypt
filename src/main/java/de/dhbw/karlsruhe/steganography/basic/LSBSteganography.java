package de.dhbw.karlsruhe.steganography.basic;

import de.dhbw.karlsruhe.steganography.Decoder;
import de.dhbw.karlsruhe.steganography.Encoder;
import de.dhbw.karlsruhe.steganography.Steganography;

import java.awt.image.BufferedImage;

public class LSBSteganography extends Steganography {


    public LSBSteganography() {
    }

    @Override
    public boolean canEncode(byte[] input, BufferedImage image) {
        return (headerSize() + encodedSize(input)) < maxSize(image);
    }

    @Override
    public String toString() {
        return "LSB Steganography";
    }

    @Override
    protected Encoder getWriter() {
        return new LSBEncoder();
    }

    @Override
    protected Decoder getDecoder() {
        return new LSBDecoder();
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
