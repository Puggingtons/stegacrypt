package de.dhbw.karlsruhe.steganography;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestImageWriteAndDecode {

    @Test
    public void testImageWriteAndDecode() {
        var image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        var bytes = "Hello World!".getBytes();

        var writer = new ImageWriter();
        var decoder = new ImageDecoder();

        var writtenImage = writer.writeData(image, bytes);
        var decodedMessage = decoder.decode(writtenImage);

        byte[] output = new byte[decodedMessage.length];

        for (int i = 0; i < decodedMessage.length; i++) {
            output[i] = decodedMessage[i];
        }

        assertArrayEquals(bytes, output);
    }
}
