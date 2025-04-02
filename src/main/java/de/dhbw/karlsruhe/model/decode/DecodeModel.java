package de.dhbw.karlsruhe.model.decode;

import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.steganography.Steganography;
import de.dhbw.karlsruhe.util.ChangeObserver;
import de.dhbw.karlsruhe.util.ListChangeObserver;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Consumer;

public class DecodeModel {

    private final ChangeObserver<BufferedImage> inputImage;
    private final ChangeObserver<byte[]> outputData;

    private final ListChangeObserver<Steganography> availableSteganographies;
    private final ListChangeObserver<Cryptography> availableCryptographies;

    private final ChangeObserver<Steganography> selectedSteganography;
    private final ChangeObserver<Cryptography> selectedCryptography;

    public DecodeModel() {
        this.inputImage = new ChangeObserver<>();
        this.outputData = new ChangeObserver<>();

        this.availableSteganographies = new ListChangeObserver<>();
        this.availableCryptographies = new ListChangeObserver<>();

        this.selectedSteganography = new ChangeObserver<>();
        this.selectedCryptography = new ChangeObserver<>();
    }

    public BufferedImage getInputImage() {
        return inputImage.get();
    }

    public void setInputImage(BufferedImage inputImage) {
        this.inputImage.set(inputImage);
    }

    public void onInputImageChange(Consumer<BufferedImage> onInputImageChange) {
        inputImage.subscribe(onInputImageChange);
    }

    public byte[] getOutputData() {
        return outputData.get();
    }

    public void setOutputData(byte[] outputData) {
        this.outputData.set(outputData);
    }

    public void onOutputDataChange(Consumer<byte[]> onOutputDataChange) {
        outputData.subscribe(onOutputDataChange);
    }

    public List<Steganography> getAvailableSteganographies() {
        return availableSteganographies.get();
    }

    public void addAvailableSteganography(Steganography steganography) {
        availableSteganographies.add(steganography);
    }

    public void onAvailableSteganographiesChange(Consumer<List<Steganography>> onAvailableSteganographyChange) {
        availableSteganographies.subscribe(onAvailableSteganographyChange);
    }

    public List<Cryptography> getAvailableCryptographies() {
        return availableCryptographies.get();
    }

    public void addAvailableCryptography(Cryptography cryptography) {
        availableCryptographies.add(cryptography);
    }

    public void onAvailableCryptographiesChange(Consumer<List<Cryptography>> onAvailableCryptographyChange) {
        availableCryptographies.subscribe(onAvailableCryptographyChange);
    }

    public Steganography getSelectedSteganography() {
        return selectedSteganography.get();
    }

    public void setSelectedSteganography(Steganography selectedSteganography) {
        this.selectedSteganography.set(selectedSteganography);
    }

    public void onSelectedSteganographyChange(Consumer<Steganography> onSelectedSteganographyChange) {
        selectedSteganography.subscribe(onSelectedSteganographyChange);
    }

    public Cryptography getSelectedCryptography() {
        return selectedCryptography.get();
    }

    public void setSelectedCryptography(Cryptography cryptography) {
        this.selectedCryptography.set(cryptography);
    }

    public void onSelectedCryptographyChange(Consumer<Cryptography> onSelectedCryptographyChange) {
        selectedCryptography.subscribe(onSelectedCryptographyChange);
    }
}
