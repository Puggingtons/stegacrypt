package de.dhbw.karlsruhe.view.keygen;

import de.dhbw.karlsruhe.view.components.FolderInputButton;
import de.dhbw.karlsruhe.view.components.VerticalTitledPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.util.function.Consumer;

public class KeygenView extends JPanel {

    private Consumer<File> onSaveFileDirectoryChangeConsumer;
    private Consumer<String> onKeyNameChangeConsumer;
    private Runnable onGenerateRunnable;

    public KeygenView() {
        setupGui();
    }

    public void setOnSaveFileDirectoryChangeConsumer(Consumer<File> onSaveFileDirectoryChangeConsumer) {
        this.onSaveFileDirectoryChangeConsumer = onSaveFileDirectoryChangeConsumer;
    }

    public void setOnGenerateRunnable(Runnable onGenerateRunnable) {
        this.onGenerateRunnable = onGenerateRunnable;
    }

    public void setOnKeyNameChangeConsumer(Consumer<String> onFileNameChangeConsumer) {
        this.onKeyNameChangeConsumer = onFileNameChangeConsumer;
    }

    private void setupGui() {
        setLayout(new BorderLayout());

        VerticalTitledPanel titledPanel = new VerticalTitledPanel("Input");

        JTextField fileNameInput = new JTextField();

        fileNameInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onKeyNameChange(fileNameInput.getText());
            }
        });

        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(_ -> {
            onGenerate();
        });

        titledPanel.add(new FolderInputButton("Choose directory...", this::onSaveFileDirectoryChange));
        titledPanel.add(fileNameInput);
        titledPanel.add(generateButton);

        add(titledPanel, BorderLayout.CENTER);
    }

    private void onSaveFileDirectoryChange(File file) {
        if (this.onSaveFileDirectoryChangeConsumer != null) {
            this.onSaveFileDirectoryChangeConsumer.accept(file);
        }
    }

    private void onGenerate() {
        if (this.onGenerateRunnable != null) {
            this.onGenerateRunnable.run();
        }
    }

    private void onKeyNameChange(String fileName) {
        if (this.onKeyNameChangeConsumer != null) {
            this.onKeyNameChangeConsumer.accept(fileName);
        }
    }
}
