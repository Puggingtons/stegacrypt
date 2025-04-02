package de.dhbw.karlsruhe.controller.decode;

import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.model.decode.DecodeModel;
import de.dhbw.karlsruhe.steganography.Steganography;

import java.awt.image.BufferedImage;

public class DecodeController {

    private final DecodeModel model;

    public DecodeController(DecodeModel model) {
        this.model = model;
    }

    public void decode() {
        Steganography steg = model.getSelectedSteganography();

        if (steg == null) {
            return;
        }

        Cryptography crypt = model.getSelectedCryptography();

        byte[] decoded = steg.decode(model.getInputImage());

        if (crypt != null) {
            decoded = crypt.decrypt(decoded);
        }

        model.setOutputData(decoded);
    }

    public void setInputImage(BufferedImage inputImage) {
        model.setInputImage(inputImage);
    }

    public void setSelectedSteganography(Steganography selectedSteganography) {
        model.setSelectedSteganography(selectedSteganography);
    }

    public void setSelectedCryptography(Cryptography cryptography) {
        model.setSelectedCryptography(cryptography);
    }
}
