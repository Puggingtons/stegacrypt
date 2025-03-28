package de.dhbw.karlsruhe.steganography;

import java.awt.image.BufferedImage;

public interface Decoder {
    byte[] decode(BufferedImage image);
}
