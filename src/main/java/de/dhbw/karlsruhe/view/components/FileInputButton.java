package de.dhbw.karlsruhe.view.components;

import javax.swing.*;
import java.io.File;
import java.util.function.Consumer;

public class FileInputButton extends JPanel {
    public FileInputButton(String buttonText, Consumer<File> onFileChange) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JButton selectImageButton = new JButton(buttonText);
        JFileChooser imageChooser = new JFileChooser("examples/");
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
