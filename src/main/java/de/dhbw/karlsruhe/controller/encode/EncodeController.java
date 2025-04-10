package de.dhbw.karlsruhe.controller.encode;

import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.model.encode.EncodeModel;
import de.dhbw.karlsruhe.steganography.Steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static de.dhbw.karlsruhe.util.BufferedImageHelper.delta;
import static de.dhbw.karlsruhe.util.FileHelper.readFileWithExtension;

public class EncodeController {

    private final EncodeModel model;

    public EncodeController(EncodeModel model) {
        this.model = model;
    }

    public void encode() {
        System.out.println("Encoding:");
        System.out.println(">          file: " + model.getInputFile());
        System.out.println("> steganography: " + model.getSteganography());
        System.out.println(">  cryptography: " + model.getCryptography());

        try {
            byte[] data = readFileWithExtension(model.getInputFile());

            if (model.getCryptography() != null) {
                data = model.getCryptography().encrypt(data);
            }

            BufferedImage steganographicEncodedImage = model.getSteganography().encode(data, model.getInputImage());
            model.setOutputImage(steganographicEncodedImage);

            model.setDeltaImage(delta(model.getInputImage(), model.getOutputImage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImage() {
        if (model.getSaveFile() == null) {
            return;
        }

        try {
            ImageIO.write(model.getOutputImage(), "png", model.getSaveFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setInputImage(BufferedImage inputImage) {
        model.setInputImage(inputImage);
    }

    public void setInputFile(File inputFile) {
        model.setInputFile(inputFile);
    }

    public void setSteganography(Steganography steganography) {
        model.setSteganography(steganography);
    }

    public void setCryptography(Cryptography cryptography) {
        model.setCryptography(cryptography);
    }

    public void addAvailableSteganography(Steganography steganography) {
        model.addAvailableSteganography(steganography);
    }

    public void addAvailableCryptography(Cryptography cryptography) {
        model.addAvailableCryptography(cryptography);
    }

    public void setSaveFile(File saveFile) {
        model.setSaveFile(saveFile);
    }
}
