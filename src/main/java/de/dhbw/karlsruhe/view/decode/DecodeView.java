package de.dhbw.karlsruhe.view.decode;

import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.steganography.Steganography;
import de.dhbw.karlsruhe.util.FileHelper;
import de.dhbw.karlsruhe.view.components.AlgorithmSelect;
import de.dhbw.karlsruhe.view.components.FileInputButton;
import de.dhbw.karlsruhe.view.components.ImageDisplay;
import de.dhbw.karlsruhe.view.components.VerticalTitledPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import static de.dhbw.karlsruhe.util.ByteHelper.bytesToString;

public class DecodeView extends JPanel {

    private final ImageDisplay inputImageDisplay;
    private final AlgorithmSelect<Steganography> steganographySelect;
    private final AlgorithmSelect<Cryptography> cryptographySelect;
    private final JPanel outputPanel;

    private Consumer<Steganography> onSteganographyChangeConsumer;
    private Consumer<Cryptography> onCryptographyChangeConsumer;
    private Consumer<BufferedImage> onInputImageChangeConsumer;
    private Consumer<File> onPrivateKeyFileChangeConsumer;

    private Runnable onDecodeRunnable;

    public DecodeView() {
        inputImageDisplay = new ImageDisplay();
        outputPanel = new VerticalTitledPanel("Output");

        steganographySelect = new AlgorithmSelect<>(this::onSteganographyChange);
        cryptographySelect = new AlgorithmSelect<>(this::onCryptographyChange);

        setupGui();
    }

    public void setAvailableSteganographies(Steganography[] steganographies) {
        steganographySelect.setModel(new DefaultComboBoxModel<>(steganographies));
    }

    public void setAvailableCryptographies(Cryptography[] cryptographies) {
        cryptographySelect.setModel(new DefaultComboBoxModel<>(cryptographies));
    }

    public void setOnSteganographyChangeConsumer(Consumer<Steganography> onSteganographyChangeConsumer) {
        this.onSteganographyChangeConsumer = onSteganographyChangeConsumer;
    }

    public void setOnCryptographyChangeConsumer(Consumer<Cryptography> onCryptographyChangeConsumer) {
        this.onCryptographyChangeConsumer = onCryptographyChangeConsumer;
    }

    public void setOnInputImageChangeConsumer(Consumer<BufferedImage> onInputImageChangeConsumer) {
        this.onInputImageChangeConsumer = onInputImageChangeConsumer;
    }

    public void setOnPrivateKeyFileChangeConsumer(Consumer<File> onPrivateKeyFileChangeConsumer) {
        this.onPrivateKeyFileChangeConsumer = onPrivateKeyFileChangeConsumer;
    }

    public void setInputImage(BufferedImage image) {
        inputImageDisplay.setImage(image);
    }

    public void setOnDecodeRunnable(Runnable onDecodeRunnable) {
        this.onDecodeRunnable = onDecodeRunnable;
    }

    public void setOutput(byte[] bytes) {
        outputPanel.removeAll();

        String extension = FileHelper.getExtension(bytes);

        switch (extension) {
            case "priv":
            case "pub":
            case "txt":
                System.out.println("creating text area");
                JTextArea textArea = new JTextArea(bytesToString(FileHelper.getData(bytes)));
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                outputPanel.add(new JScrollPane(textArea));
                break;
            case "jpg":
            case "jpeg":
            case "gif":
            case "bmp":
            case "png":
                System.out.println("creating image display");
                ImageDisplay imageDisplay = new ImageDisplay();
                try {
                    imageDisplay.setImage(ImageIO.read(new ByteArrayInputStream(FileHelper.getData(bytes))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                outputPanel.add(imageDisplay);
                break;
            default:
                System.out.println("extension not found!");
        }

        outputPanel.revalidate();
    }

    private void setupGui() {
        setLayout(new BorderLayout());

        add(createInputPanel(), BorderLayout.WEST);
        add(outputPanel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        VerticalTitledPanel inputPanel = new VerticalTitledPanel("Input");

        inputPanel.add(inputImageDisplay);
        inputPanel.add(new FileInputButton("Choose image...", this::onInputImageChange));

        inputPanel.add(steganographySelect);
        inputPanel.add(cryptographySelect);

        inputPanel.add(new FileInputButton("Choose encryption key", this::onPrivateKeyFileChangeConsumerChange, "keys/"));

        JButton encodeButton = new JButton("Decode");
        encodeButton.addActionListener(_ -> {
            if (onDecodeRunnable != null) {
                onDecodeRunnable.run();
            }
        });

        inputPanel.add(encodeButton);

        return inputPanel;
    }

    private void onInputImageChange(File file) {
        try {
            BufferedImage image = ImageIO.read(file);

            if (onInputImageChangeConsumer != null) {
                onInputImageChangeConsumer.accept(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onSteganographyChange(Steganography steganography) {
        if (onSteganographyChangeConsumer != null) {
            onSteganographyChangeConsumer.accept(steganography);
        }
    }

    private void onCryptographyChange(Cryptography cryptography) {
        if (onCryptographyChangeConsumer != null) {
            onCryptographyChangeConsumer.accept(cryptography);
        }
    }

    private void onPrivateKeyFileChangeConsumerChange(File file) {
        if (onPrivateKeyFileChangeConsumer != null) {
            onPrivateKeyFileChangeConsumer.accept(file);
        }
    }
}
