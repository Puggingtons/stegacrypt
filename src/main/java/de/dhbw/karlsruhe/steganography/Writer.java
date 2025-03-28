package de.dhbw.karlsruhe.steganography;

import java.awt.image.BufferedImage;

public interface Writer {
    BufferedImage writeData(BufferedImage image, byte[] data);
}
