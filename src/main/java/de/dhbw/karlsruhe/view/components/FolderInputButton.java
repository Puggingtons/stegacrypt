package de.dhbw.karlsruhe.view.components;

import javax.swing.*;
import java.io.File;
import java.util.function.Consumer;

public class FolderInputButton extends FileInputButton {
    public FolderInputButton(String buttonText, Consumer<File> onFileChange) {
        this(buttonText, onFileChange, "keys/");
    }

    public FolderInputButton(String buttonText, Consumer<File> onFileChange, String initialPath) {
        super(buttonText, onFileChange, initialPath);
        imageChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
}
