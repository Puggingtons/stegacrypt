package de.dhbw.karlsruhe.steganography;

import java.awt.image.BufferedImage;

public interface Encoder {
    BufferedImage writeData(BufferedImage image, byte[] data);
}
