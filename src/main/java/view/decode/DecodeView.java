package view.decode;

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

public class DecodeView extends JPanel {

    private final ImageDisplay inputImageDisplay;

    private final JComboBox<Steganography> steganographySelect;
    private final JComboBox<Cryptography> cryptographySelect;

    private Consumer<Steganography> onSteganographyChangeConsumer;
    private Consumer<Cryptography> onCryptographyChangeConsumer;

    private Consumer<BufferedImage> onInputImageChangeConsumer;

    public DecodeView() {
        inputImageDisplay = new ImageDisplay();

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

    public void setInputImage(BufferedImage image) {
        inputImageDisplay.setImage(image);
    }

    private void setupGui() {
        setLayout(new BorderLayout());

        add(createInputPanel(), BorderLayout.WEST);
    }

    private JPanel createInputPanel() {
        VerticalTitledPanel inputPanel = new VerticalTitledPanel("Input");

        inputPanel.add(inputImageDisplay);
        inputPanel.add(new FileInputButton("Choose image...", this::onInputImageChange));

        inputPanel.add(steganographySelect);
        inputPanel.add(cryptographySelect);

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
}
