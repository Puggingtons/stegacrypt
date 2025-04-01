package view.encode;

import de.dhbw.karlsruhe.cryptography.Cryptography;
import de.dhbw.karlsruhe.steganography.Steganography;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class EncodeView extends JPanel {

    private final JLabel inputImageLabel;
    private final JLabel outputImageLabel;

    private final JComboBox<Steganography> steganographySelect;
    private final JComboBox<Cryptography> cryptographySelect;

    private Consumer<BufferedImage> onInputImageChangeConsumer;
    private Consumer<File> onInputFileChangeConsumer;

    public EncodeView() {
        inputImageLabel = new JLabel();
        outputImageLabel = new JLabel();

        steganographySelect = new JComboBox<>();
        cryptographySelect = new JComboBox<>();

        setupGui();
    }

    public void setInputImage(BufferedImage inputImage) {
        inputImageLabel.setIcon(new ImageIcon(inputImage));
    }

    public void setOutputImage(BufferedImage outputImage) {
        outputImageLabel.setIcon(new ImageIcon(outputImage));
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

    private void setupGui() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        setupInputs();
    }

    private void setupInputs() {
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        inputPanel.add(createInputButton("Bild auswählen...", this::onInputImageChange));
        inputPanel.add(inputImageLabel);
        inputPanel.add(createInputButton("Datei auswählen...", this::onInputFileChange));

        inputPanel.add(steganographySelect);
        inputPanel.add(cryptographySelect);

        add(inputPanel);
    }

    private JButton createInputButton(String buttonText, Consumer<File> onFileChange) {
        JFileChooser imageChooser = new JFileChooser("examples/");
        JButton selectImageButton = new JButton(buttonText);

        selectImageButton.addActionListener(_ -> {
            int returnVal = imageChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                onFileChange.accept(imageChooser.getSelectedFile());
            }
        });

        return selectImageButton;
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
}
