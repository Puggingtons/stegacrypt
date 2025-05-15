package de.dhbw.karlsruhe.steganography;

import de.dhbw.karlsruhe.steganography.stegacrypt.StegaCryptDecoder;
import de.dhbw.karlsruhe.steganography.stegacrypt.StegaCryptEncoder;
import de.dhbw.karlsruhe.util.FileHelper;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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

    @Test
    public void itEncodesInDifferentBitPlanes() throws Exception {
        var image = ImageIO.read(new File("examples/input/dhbw_logo.png"));
        var bytes = FileHelper.readFileWithExtension(new File("examples/input/bee_movie_script.txt"));

        for (int i = 1; i <= 8; i++) {
            var writer = new StegaCryptEncoder(i);
            var writtenImage = writer.writeData(image, bytes);

            ImageIO.write(writtenImage, "png", new File("examples/output/dhbw_stegacrypt_" + i + ".png"));
        }
    }

}
