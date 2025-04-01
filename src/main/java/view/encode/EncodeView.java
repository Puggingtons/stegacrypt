package view.encode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class EncodeView extends JPanel {

    private final JLabel inputImageLabel;
    private final JLabel outputImageLabel;

    private Consumer<BufferedImage> onInputImageChange;
    private Consumer<File> onInputFileChange;

    public EncodeView() {
        inputImageLabel = new JLabel();
        outputImageLabel = new JLabel();

        setupGui();
    }

    public void setInputImage(BufferedImage inputImage) {
        inputImageLabel.setIcon(new ImageIcon(inputImage));
    }

    public void setOutputImage(BufferedImage outputImage) {
        outputImageLabel.setIcon(new ImageIcon(outputImage));
    }

    public void setOnInputImageChange(Consumer<BufferedImage> onInputImageChange) {
        this.onInputImageChange = onInputImageChange;
    }

    public void setOnInputFileChange(Consumer<File> onInputFileChange) {
        this.onInputFileChange = onInputFileChange;
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

            if (onInputImageChange != null) {
                onInputImageChange.accept(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onInputFileChange(File file) {
        if (onInputFileChange != null) {
            onInputFileChange.accept(file);
        }
    }
}
