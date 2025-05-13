package de.dhbw.karlsruhe.controller.encode;

import de.dhbw.karlsruhe.cryptography.NoCryptography;
import de.dhbw.karlsruhe.model.encode.EncodeModel;
import de.dhbw.karlsruhe.steganography.basic.LSBDecoder;
import de.dhbw.karlsruhe.steganography.basic.LSBSteganography;
import de.dhbw.karlsruhe.steganography.stegacrypt.StegaCryptDecoder;
import de.dhbw.karlsruhe.steganography.stegacrypt.StegaCryptSteganography;
import de.dhbw.karlsruhe.util.ByteHelper;
import de.dhbw.karlsruhe.util.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class EncodeControllerTest {

    File secretFile;
    BufferedImage inputImage;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) throws Exception {
        secretFile = Path.of("examples/input/bee_movie_script.txt").toFile();
        File imageFile = Path.of("examples/input/bee-movie.png").toFile();
        inputImage = ImageIO.read(imageFile);

        inputImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    public void testItEncodes() throws IOException {
        StegaCryptDecoder decoder = new StegaCryptDecoder();

        EncodeModel model = new EncodeModel();
        EncodeController controller = new EncodeController(model);

        model.setCryptography(new NoCryptography());
        model.setSteganography(new StegaCryptSteganography());

        model.setInputFile(secretFile);
        model.setInputImage(inputImage);

        controller.encode();

        byte[] decoded = decoder.decode(model.getOutputImage());
        System.out.println(FileHelper.getExtension(decoded));
        System.out.println(ByteHelper.bytesToString(decoded));
    }

    @Test
    public void testItEncodesLSB() throws IOException {
        LSBDecoder decoder = new LSBDecoder();

        EncodeModel model = new EncodeModel();
        EncodeController controller = new EncodeController(model);

        model.setCryptography(new NoCryptography());
        model.setSteganography(new LSBSteganography());

        model.setInputFile(secretFile);
        model.setInputImage(inputImage);

        controller.encode();

        byte[] decoded = decoder.decode(model.getOutputImage());
        System.out.println(FileHelper.getExtension(decoded));
        System.out.println(ByteHelper.bytesToString(decoded));
    }
}
