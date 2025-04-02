package view.encode;

import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.steganography.Steganography;
import view.components.FileInputButton;
import view.components.ImageDisplay;
import view.components.VerticalTitledPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class EncodeView extends JPanel {
    private final ImageDisplay inputImageDisplay;
    private final ImageDisplay outputImageDisplay;
    private final ImageDisplay deltaImageDisplay;

    private final JComboBox<Steganography> steganographySelect;
    private final JComboBox<Cryptography> cryptographySelect;

    private Consumer<BufferedImage> onInputImageChangeConsumer;
    private Consumer<File> onInputFileChangeConsumer;
    private Consumer<Steganography> onSteganographyChangeConsumer;
    private Consumer<Cryptography> onCryptographyChangeConsumer;

    private Consumer<File> onSaveFileChangeConsumer;

    private Runnable onEncodeRunnable;
    private Runnable onSaveRunnable;

    public EncodeView() {
        inputImageDisplay = new ImageDisplay();
        outputImageDisplay = new ImageDisplay();
        deltaImageDisplay = new ImageDisplay();

        steganographySelect = new JComboBox<>();
        cryptographySelect = new JComboBox<>();

        steganographySelect.addActionListener(_ -> {
            this.onSteganographyChange((Steganography) steganographySelect.getSelectedItem());
        });
        cryptographySelect.addActionListener(_ -> {
            this.onCryptographyChange((Cryptography) cryptographySelect.getSelectedItem());
        });

        setupGui();
    }

    public void setInputImage(BufferedImage inputImage) {
        inputImageDisplay.setImage(inputImage);
    }

    public void setOutputImage(BufferedImage outputImage) {
        outputImageDisplay.setImage(outputImage);
    }

    public void setDeltaImage(BufferedImage deltaImage) {
        deltaImageDisplay.setImage(deltaImage);
    }

    public void setAvailableSteganographies(Steganography[] steganographies) {
        steganographySelect.setModel(new DefaultComboBoxModel<>(steganographies));
    }

    public void setAvailableCryptographies(Cryptography[] cryptographies) {
        cryptographySelect.setModel(new DefaultComboBoxModel<>(cryptographies));
    }

    public void setOnInputImageChangeConsumer(Consumer<BufferedImage> onInputImageChangeConsumer) {
        this.onInputImageChangeConsumer = onInputImageChangeConsumer;
    }

    public void setOnInputFileChangeConsumer(Consumer<File> onInputFileChangeConsumer) {
        this.onInputFileChangeConsumer = onInputFileChangeConsumer;
    }

    public void setOnSaveFileChangeConsumer(Consumer<File> onSaveFileChangeConsumer) {
        this.onSaveFileChangeConsumer = onSaveFileChangeConsumer;
    }

    public void setOnEncodeRunnable(Runnable onEncodeRunnable) {
        this.onEncodeRunnable = onEncodeRunnable;
    }

    public void setOnSaveRunnable(Runnable onSaveRunnable) {
        this.onSaveRunnable = onSaveRunnable;
    }

    public void setOnSteganographyChangeConsumer(Consumer<Steganography> onSteganographyChangeConsumer) {
        this.onSteganographyChangeConsumer = onSteganographyChangeConsumer;
    }

    public void setOnCryptographyChangeConsumer(Consumer<Cryptography> onCryptographyChangeConsumer) {
        this.onCryptographyChangeConsumer = onCryptographyChangeConsumer;
    }

    private void setupGui() {
        setLayout(new BorderLayout());

        add(createInputPanel(), BorderLayout.WEST);
        add(createEncodePanel(), BorderLayout.CENTER);
        add(createOutputPanel(), BorderLayout.EAST);
    }

    private JPanel createInputPanel() {
        VerticalTitledPanel inputPanel = new VerticalTitledPanel("Input");

        VerticalTitledPanel inputImagePanel = new VerticalTitledPanel("Input Image");
        inputImagePanel.add(new FileInputButton("Choose image...", this::onInputImageChange));
        inputImagePanel.add(inputImageDisplay);
        inputPanel.add(inputImagePanel);

        VerticalTitledPanel inputFilePanel = new VerticalTitledPanel("Input file");
        inputFilePanel.add(new FileInputButton("Choose file...", this::onInputFileChange));
        inputFilePanel.add(Box.createHorizontalGlue());
        inputPanel.add(inputFilePanel);

        return inputPanel;
    }

    private JPanel createEncodePanel() {
        VerticalTitledPanel encodePanel = new VerticalTitledPanel("Encode");

        JButton encodeButton = new JButton("Encode");
        encodeButton.addActionListener(_ -> {
            if (onEncodeRunnable != null) {
                onEncodeRunnable.run();
            }
        });

        steganographySelect.setMaximumSize(new Dimension(200, 25));
        cryptographySelect.setMaximumSize(new Dimension(200, 25));

        // encodePanel.add(Box.createVerticalGlue());
        encodePanel.add(steganographySelect);
        encodePanel.add(cryptographySelect);

        encodePanel.add(encodeButton);

        return encodePanel;
    }

    private JPanel createOutputPanel() {
        VerticalTitledPanel outputPanel = new VerticalTitledPanel("Output");

        outputPanel.add(outputImageDisplay);
        outputPanel.add(new FileInputButton("Choose save file...", this::onSaveFileChange));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(_ -> {
            onSave();
        });
        outputPanel.add(saveButton);

        outputPanel.add(deltaImageDisplay);

        return outputPanel;

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

    private void onInputFileChange(File file) {
        if (onInputFileChangeConsumer != null) {
            onInputFileChangeConsumer.accept(file);
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

    private void onSaveFileChange(File file) {
        if (onSaveFileChangeConsumer != null) {
            onSaveFileChangeConsumer.accept(file);
        }
    }

    private void onSave() {
        if (onSaveRunnable != null) {
            onSaveRunnable.run();
        }
    }
}
