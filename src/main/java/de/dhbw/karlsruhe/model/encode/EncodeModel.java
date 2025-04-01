package de.dhbw.karlsruhe.model.encode;

import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.steganography.Steganography;
import de.dhbw.karlsruhe.util.ChangeObserver;
import de.dhbw.karlsruhe.util.ListChangeObserver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class EncodeModel {
    private final ChangeObserver<BufferedImage> inputImage;
    private final ChangeObserver<File> inputFile;
    private final ChangeObserver<Steganography> steganography;
    private final ChangeObserver<Cryptography> cryptography;
    private final ChangeObserver<BufferedImage> outputImage;

    private ListChangeObserver<Steganography> availableSteganographies;
    private ListChangeObserver<Cryptography> availableCryptographies;

    public EncodeModel() {
        inputImage = new ChangeObserver<>();
        inputFile = new ChangeObserver<>();
        steganography = new ChangeObserver<>();
        cryptography = new ChangeObserver<>();
        outputImage = new ChangeObserver<>();

        availableSteganographies = new ListChangeObserver<>();
        availableCryptographies = new ListChangeObserver<>();
    }

    public void onInputImageChange(Consumer<BufferedImage> onImageChange) {
        inputImage.subscribe(onImageChange);
    }

    public void onInputFileChange(Consumer<File> onFileChange) {
        inputFile.subscribe(onFileChange);
    }

    public void onSteganographyChange(Consumer<Steganography> onSteganographyChange) {
        steganography.subscribe(onSteganographyChange);
    }

    public void onCryptographyChange(Consumer<Cryptography> onCryptographyChange) {
        cryptography.subscribe(onCryptographyChange);
    }

    public void onOutputImageChange(Consumer<BufferedImage> onOutputChange) {
        outputImage.subscribe(onOutputChange);
    }

    public void onAvailableSteganographiesChange(Consumer<List<Steganography>> onAvailableSteganographiesChange) {
        availableSteganographies.subscribe(onAvailableSteganographiesChange);
    }

    public void onAvailableCryptographiesChange(Consumer<List<Cryptography>> onAvailableCryptographiesChange) {
        availableCryptographies.subscribe(onAvailableCryptographiesChange);
    }

    public BufferedImage getInputImage() {
        return inputImage.get();
    }

    public void setInputImage(BufferedImage image) {
        inputImage.set(image);
    }

    public File getInputFile() {
        return inputFile.get();
    }

    public void setInputFile(File file) {
        inputFile.set(file);
    }

    public Steganography getSteganography() {
        return steganography.get();
    }

    public void setSteganography(Steganography steganography) {
        this.steganography.set(steganography);
    }

    public Cryptography getCryptography() {
        return cryptography.get();
    }

    public void setCryptography(Cryptography cryptography) {
        this.cryptography.set(cryptography);
    }

    public BufferedImage getOutputImage() {
        return outputImage.get();
    }

    public void setOutputImage(BufferedImage image) {
        outputImage.set(image);
    }

    public List<Steganography> getAvailableSteganographies() {
        return this.availableSteganographies.get();
    }

    public void addAvailableSteganography(Steganography steganography) {
        availableSteganographies.add(steganography);
    }

    public List<Cryptography> getAvailableCryptographies() {
        return availableCryptographies.get();
    }

    public void addAvailableCryptography(Cryptography availableCryptography) {
        this.availableCryptographies.add(availableCryptography);
    }
}
