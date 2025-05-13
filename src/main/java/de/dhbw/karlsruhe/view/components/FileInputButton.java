package de.dhbw.karlsruhe.view.components;

import javax.swing.*;
import java.io.File;
import java.util.function.Consumer;

public class FileInputButton extends JPanel {

    protected JFileChooser imageChooser;


    public FileInputButton(String buttonText, Consumer<File> onFileChange) {
        this(buttonText, onFileChange, "examples/");
    }

    public FileInputButton(String buttonText, Consumer<File> onFileChange, String initialPath) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JButton selectImageButton = new JButton(buttonText);
        imageChooser = new JFileChooser(initialPath);
        JLabel selectedFileLabel = new JLabel();

        selectImageButton.addActionListener(_ -> {
            int returnVal = imageChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                onFileChange.accept(imageChooser.getSelectedFile());
                selectedFileLabel.setText(imageChooser.getSelectedFile().getName());
            }
        });

        add(selectImageButton);
        add(selectedFileLabel);
    }
}
