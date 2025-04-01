package de.dhbw.karlsruhe.controller.encode;

import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.model.encode.EncodeModel;
import de.dhbw.karlsruhe.steganography.Steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class EncodeController {

    private final EncodeModel model;

    public EncodeController(EncodeModel model) {
        this.model = model;
    }

    public void encode() throws IOException {
        byte[] data = Files.readAllBytes(model.getInputFile().toPath());
        byte[] encryptedData = model.getCryptography().encrypt(data);

        BufferedImage steganographicEncodedImage = model.getSteganography().encode(encryptedData, model.getInputImage());
        model.setOutputImage(steganographicEncodedImage);
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


}
