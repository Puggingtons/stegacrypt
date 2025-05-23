package de.dhbw.karlsruhe.steganography;

import de.dhbw.karlsruhe.steganography.basic.LSBDecoder;
import de.dhbw.karlsruhe.steganography.basic.LSBEncoder;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestImageWriteAndDecode {

    @Test
    public void testImageWriteAndDecode() {
        var image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        var bytes = "Hello World!".getBytes();

        var writer = new LSBEncoder();
        var decoder = new LSBDecoder();

        var writtenImage = writer.writeData(image, bytes);
        var decodedMessage = decoder.decode(writtenImage);

        assertArrayEquals(bytes, decodedMessage);
    }
}
