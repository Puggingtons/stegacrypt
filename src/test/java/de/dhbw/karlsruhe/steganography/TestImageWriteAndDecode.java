package de.dhbw.karlsruhe.steganography;

import de.dhbw.karlsruhe.steganography.basic.BasicDecoder;
import de.dhbw.karlsruhe.steganography.basic.BasicEncoder;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestImageWriteAndDecode {

    @Test
    public void testImageWriteAndDecode() {
        var image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        var bytes = "Hello World!".getBytes();

        var writer = new BasicEncoder();
        var decoder = new BasicDecoder();

        var writtenImage = writer.writeData(image, bytes);
        var decodedMessage = decoder.decode(writtenImage);

        assertArrayEquals(bytes, decodedMessage);
    }
}
