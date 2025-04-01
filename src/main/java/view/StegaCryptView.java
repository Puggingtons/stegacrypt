package view;

import de.dhbw.karlsruhe.controller.encode.EncodeController;
import de.dhbw.karlsruhe.model.encode.EncodeModel;
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

        encodeView.setOnInputFileChange(encodeController::setInputFile);
        encodeView.setOnInputImageChange(encodeController::setInputImage);

        encodeModel.onInputImageChange(encodeView::setInputImage);

        tabs.addTab("Encode", encodeView);
    }
}
