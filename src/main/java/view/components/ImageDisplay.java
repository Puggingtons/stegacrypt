package view.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDisplay extends JLabel {

    private final int maxWidth;
    private final int maxHeight;

    private static BufferedImage loadDefaultImage() {
        try {
            return ImageIO.read(new File("examples/placeholder.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ImageDisplay() {
        this(loadDefaultImage());
    }

    public ImageDisplay(BufferedImage image) {
        this(image, 200, 200);
    }

    public ImageDisplay(BufferedImage image, int maxWidth, int maxHeight) {
        super();
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;

        setImage(image);
    }

    public void setImage(BufferedImage image) {
        int largerSide = Math.max(image.getHeight(), image.getWidth());

        int scaledWidth = (int) ((maxWidth / (double) largerSide) * image.getWidth());
        int scaledHeight = (int) ((maxHeight / (double) largerSide) * image.getHeight());

        setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(scaledWidth, scaledHeight, BufferedImage.SCALE_SMOOTH)));
    }
}
