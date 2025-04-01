package de.dhbw.karlsruhe.steganography;

import java.awt.image.BufferedImage;

public abstract class Steganography {
    public BufferedImage encode(byte[] input, BufferedImage image) {
        if (!canEncode(input, image)) {
            throw new IllegalArgumentException("input does not fit in the image");
        }

        return getWriter().writeData(image, input);
    }

    public byte[] decode(BufferedImage image) {
        return getDecoder().decode(image);
    }

    protected abstract boolean canEncode(byte[] input, BufferedImage image);

    protected abstract Encoder getWriter();

    protected abstract Decoder getDecoder();
}
