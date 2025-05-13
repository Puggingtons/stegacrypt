package de.dhbw.karlsruhe.steganography;

import de.dhbw.karlsruhe.steganography.stegacrypt.StegaCryptDecoder;
import de.dhbw.karlsruhe.steganography.stegacrypt.StegaCryptEncoder;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StegaCryptSteganographyTest {

    @Test
    public void itEncodesAndDecodes() {
        var image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        var bytes = new byte[]{(byte) 0b01100110};

        var writer = new StegaCryptEncoder(8);
        var decoder = new StegaCryptDecoder();

        var writtenImage = writer.writeData(image, bytes);
        var decodedMessage = decoder.decode(writtenImage);

        assertArrayEquals(bytes, decodedMessage);
    }

}
