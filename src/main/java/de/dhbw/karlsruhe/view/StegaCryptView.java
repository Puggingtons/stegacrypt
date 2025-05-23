package de.dhbw.karlsruhe.view;

import de.dhbw.karlsruhe.controller.decode.DecodeController;
import de.dhbw.karlsruhe.controller.encode.EncodeController;
import de.dhbw.karlsruhe.controller.keygen.KeygenController;
import de.dhbw.karlsruhe.cryptography.AESInRSACryptography;
import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.cryptography.NoCryptography;
import de.dhbw.karlsruhe.model.decode.DecodeModel;
import de.dhbw.karlsruhe.model.encode.EncodeModel;
import de.dhbw.karlsruhe.model.keygen.KeygenModel;
import de.dhbw.karlsruhe.steganography.Steganography;
import de.dhbw.karlsruhe.steganography.basic.LSBSteganography;
import de.dhbw.karlsruhe.steganography.stegacrypt.StegaCryptSteganography;
import de.dhbw.karlsruhe.view.decode.DecodeView;
import de.dhbw.karlsruhe.view.encode.EncodeView;
import de.dhbw.karlsruhe.view.keygen.KeygenView;

import javax.swing.*;

public class StegaCryptView extends JFrame {
    private final JTabbedPane tabs;

    private Steganography[] steganographies;
    private Cryptography[] cryptographies;

    public StegaCryptView() {
        setTitle("Stega Crypt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);

        steganographies = new Steganography[]{new StegaCryptSteganography(), new LSBSteganography()};
        cryptographies = new Cryptography[]{new AESInRSACryptography(), new NoCryptography()/*, new RSACryptography(), new AESCryptography()*/};

        tabs = new JTabbedPane();

        tabs.addTab("Encode", setupEncodeTab());
        tabs.addTab("Decode", setupDecodeTab());
        tabs.addTab("KeyGen", setupKeygenTab());

        add(tabs);
    }

    private JPanel setupEncodeTab() {
        EncodeView encodeView = new EncodeView();
        EncodeModel encodeModel = new EncodeModel();
        EncodeController encodeController = new EncodeController(encodeModel);

        connectEncodeViewAndController(encodeView, encodeController);
        connectEncodeModelAndView(encodeModel, encodeView);

        // setup available steganographies and cryptographies
        for (Steganography steganography : steganographies) {
            encodeController.addAvailableSteganography(steganography);
        }
        encodeController.setSteganography(steganographies[0]);

        for (Cryptography cryptography : cryptographies) {
            encodeController.addAvailableCryptography(cryptography);
        }
        encodeController.setCryptography(cryptographies[0]);


        return encodeView;
    }

    private void connectEncodeViewAndController(EncodeView encodeView, EncodeController encodeController) {
        encodeView.setOnInputFileChangeConsumer(encodeController::setInputFile);
        encodeView.setOnInputImageChangeConsumer(encodeController::setInputImage);

        encodeView.setOnSteganographyChangeConsumer(encodeController::setSteganography);
        encodeView.setOnCryptographyChangeConsumer(encodeController::setCryptography);

        encodeView.setOnEncodeRunnable(encodeController::encode);

        encodeView.setOnSaveFileChangeConsumer(encodeController::setSaveFile);
        encodeView.setOnSaveRunnable(encodeController::saveImage);

        encodeView.setOnPublicKeyFileChangeConsumer(encodeController::setPublicKeyFromFile);
    }

    private void connectEncodeModelAndView(EncodeModel encodeModel, EncodeView encodeView) {
        encodeModel.onInputImageChange(encodeView::setInputImage);
        encodeModel.onOutputImageChange(encodeView::setOutputImage);
        encodeModel.onDeltaImageChange(encodeView::setDeltaImage);
        encodeModel.onAvailableSteganographiesChange((s) -> {
            encodeView.setAvailableSteganographies(s.toArray(new Steganography[0]));
        });
        encodeModel.onAvailableCryptographiesChange((s) -> {
            encodeView.setAvailableCryptographies(s.toArray(new Cryptography[0]));
        });
    }

    private JPanel setupDecodeTab() {
        DecodeView decodeView = new DecodeView();
        DecodeModel decodeModel = new DecodeModel();
        DecodeController decodeController = new DecodeController(decodeModel);

        connectDecodeViewAndController(decodeView, decodeController);
        connectDecodeModelAndView(decodeModel, decodeView);

        for (Steganography steganography : steganographies) {
            decodeController.addAvailableSteganography(steganography);
        }
        decodeController.setSelectedSteganography(steganographies[0]);

        for (Cryptography cryptography : cryptographies) {
            decodeController.addAvailableCryptography(cryptography);
        }
        decodeController.setSelectedCryptography(cryptographies[0]);


        return decodeView;
    }

    private void connectDecodeViewAndController(DecodeView decodeView, DecodeController decodeController) {
        decodeView.setOnSteganographyChangeConsumer(decodeController::setSelectedSteganography);
        decodeView.setOnCryptographyChangeConsumer(decodeController::setSelectedCryptography);
        decodeView.setOnInputImageChangeConsumer(decodeController::setInputImage);
        decodeView.setOnDecodeRunnable(decodeController::decode);
        decodeView.setOnPrivateKeyFileChangeConsumer(decodeController::setPrivateKeyFromFile);
    }

    private void connectDecodeModelAndView(DecodeModel decodeModel, DecodeView decodeView) {
        decodeModel.onInputImageChange(decodeView::setInputImage);
        decodeModel.onAvailableSteganographiesChange((s) -> {
            decodeView.setAvailableSteganographies(s.toArray(new Steganography[0]));
        });
        decodeModel.onAvailableCryptographiesChange((s) -> {
            decodeView.setAvailableCryptographies(s.toArray(new Cryptography[0]));
        });
        decodeModel.onOutputDataChange(decodeView::setOutput);
    }

    private JPanel setupKeygenTab() {
        KeygenView keygenView = new KeygenView();
        KeygenModel keygenModel = new KeygenModel();
        KeygenController keygenController = new KeygenController(keygenModel);

        connectKeyGenViewAndController(keygenView, keygenController);
        connectKeygenModelAndView(keygenModel, keygenView);

        return keygenView;
    }

    private void connectKeyGenViewAndController(KeygenView keygenView, KeygenController keygenController) {
        keygenView.setOnGenerateRunnable(keygenController::generateAndSaveKeyPair);
        keygenView.setOnKeyNameChangeConsumer(keygenController::setKeyName);
        keygenView.setOnSaveFileDirectoryChangeConsumer(keygenController::setSaveFileDirectory);
    }

    private void connectKeygenModelAndView(KeygenModel keygenModel, KeygenView keygenView) {

    }
}
