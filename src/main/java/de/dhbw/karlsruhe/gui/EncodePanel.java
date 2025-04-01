package de.dhbw.karlsruhe.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EncodePanel extends JPanel {

    private final String DEFAULT_PATH = "examples/";

    JLabel inputImagelabel;
    private BufferedImage selectedInput;
    private File selectedFile;

    public EncodePanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        setupInputs();
        add(new JSeparator(SwingConstants.VERTICAL));
        setupEncode();
        add(new JSeparator(SwingConstants.VERTICAL));
        setupOutput();
    }

    private void setupInputs() {
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JFileChooser imageChooser = new JFileChooser(DEFAULT_PATH);
        JButton selectImage = new JButton("Bild auswählen...");

        selectImage.addActionListener(_ -> {
            int returnVal = imageChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                setImage(imageChooser.getSelectedFile());
            }
        });

        inputPanel.add(selectImage);

        JFileChooser inputFileChooser = new JFileChooser(DEFAULT_PATH);
        JButton selectInputFile = new JButton("Datei auswählen...");

        selectInputFile.addActionListener(_ -> {
            int returnVal = inputFileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                setFile(imageChooser.getSelectedFile());
            }
        });

        inputPanel.add(selectInputFile);

        add(inputPanel);
    }

    private void setupEncode() {
        JPanel encodePanel = new JPanel();
        encodePanel.setLayout(new BoxLayout(encodePanel, BoxLayout.Y_AXIS));

        inputImagelabel = new JLabel();
        inputImagelabel.setIcon(new ImageIcon());
        encodePanel.add(inputImagelabel);

        JButton encodeButton = new JButton("Encode");

        add(encodePanel);
    }

    private void setupOutput() {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

        add(outputPanel);
    }

    private void setImage(File file) {
        try {
            selectedInput = ImageIO.read(file);
            inputImagelabel.setIcon(new ImageIcon(selectedInput));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void setFile(File file) {
        this.selectedFile = file;
    }
}
