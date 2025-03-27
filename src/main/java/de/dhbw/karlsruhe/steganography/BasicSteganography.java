package de.dhbw.karlsruhe.steganography;

import java.awt.image.BufferedImage;

public class BasicSteganography implements Steganography {
    @Override
    public BufferedImage encode(byte[] input, BufferedImage image) {
        if (!canEncode(input, image)) {
            throw new IllegalArgumentException("input does not fit in the image");
        }

        ImageWriter writer = new ImageWriter(image);
        writer.writeData(input);
        return writer.getImage();
    }

    @Override
    public byte[] decode(BufferedImage image) {
        ImageDecoder imageDecoder = new ImageDecoder(image);
        Byte[] result = imageDecoder.decode();

        byte[] bytes = new byte[result.length];

        for (int i = 0; i < result.length; i++) {
            bytes[i] = result[i];
        }

        return bytes;
    }

    @Override
    public boolean canEncode(byte[] input, BufferedImage image) {
        return input.length * 8 < image.getWidth() * image.getHeight();
    }
}
