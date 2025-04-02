package view;

import de.dhbw.karlsruhe.controller.encode.EncodeController;
import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.cryptography.NoCryptography;
import de.dhbw.karlsruhe.model.encode.EncodeModel;
import de.dhbw.karlsruhe.steganography.Steganography;
import de.dhbw.karlsruhe.steganography.basic.BasicSteganography;
import view.encode.EncodeView;

import javax.swing.*;

public class StegaCryptView extends JFrame {
    private final JTabbedPane tabs;

    public StegaCryptView() {
        setTitle("Stega Crypt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);

        tabs = new JTabbedPane();

        setupEncodeTab();

        add(tabs);
    }

    private void setupEncodeTab() {
        EncodeView encodeView = new EncodeView();
        EncodeModel encodeModel = new EncodeModel();
        EncodeController encodeController = new EncodeController(encodeModel);

        connectViewAndController(encodeView, encodeController);
        connectModelAndView(encodeModel, encodeView);

        // setup available steganographies and cryptographies
        Steganography basic = new BasicSteganography();
        encodeController.addAvailableSteganography(basic);
        encodeController.setSteganography(basic);

        Cryptography noCrypt = new NoCryptography();
        encodeController.addAvailableCryptography(noCrypt);
        encodeController.setCryptography(noCrypt);

        tabs.addTab("Encode", encodeView);
    }

    private void connectViewAndController(EncodeView encodeView, EncodeController encodeController) {
        encodeView.setOnInputFileChangeConsumer(encodeController::setInputFile);
        encodeView.setOnInputImageChangeConsumer(encodeController::setInputImage);

        encodeView.setOnSteganographyChangeConsumer(encodeController::setSteganography);
        encodeView.setOnCryptographyChangeConsumer(encodeController::setCryptography);

        encodeView.setOnEncodeRunnable(encodeController::encode);

        encodeView.setOnSaveFileChangeConsumer(encodeController::setSaveFile);
        encodeView.setOnSaveRunnable(encodeController::saveImage);
    }

    private void connectModelAndView(EncodeModel encodeModel, EncodeView encodeView) {
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
}
