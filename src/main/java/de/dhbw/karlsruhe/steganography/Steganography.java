package de.dhbw.karlsruhe.steganography;

import java.awt.image.BufferedImage;

public interface Steganography {
    BufferedImage encode(byte[] input, BufferedImage image);

    byte[] decode(BufferedImage image);

    boolean canEncode(byte[] input, BufferedImage image);
}
