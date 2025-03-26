package de.dhbw.karlsruhe;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Security;

public class Main {
    public static void main(String[] args) throws IOException {
        setupSecurity();

        BufferedImage img = ImageIO.read(new File("examples/lenna.png"));

        int bit_per_color = 0b1111_1111;
        int a = bit_per_color << 24;
        int r = bit_per_color << 16;
        int g = bit_per_color << 8;
        int b = bit_per_color;

        System.out.println(leftPad(Integer.toBinaryString(a), 32, '0'));
        System.out.println(leftPad(Integer.toBinaryString(r), 32, '0'));
        System.out.println(leftPad(Integer.toBinaryString(g), 32, '0'));
        System.out.println(leftPad(Integer.toBinaryString(b), 32, '0'));


        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                img.setRGB(x, y, img.getRGB(x, y) & (b | g));
            }
        }

        img.flush();

        ImageIcon icon = new ImageIcon(img);

        JFrame frame = new JFrame();

        frame.setLayout(new FlowLayout());
        frame.setSize(512, 512);

        JLabel lbl = new JLabel();
        lbl.setIcon(icon);

        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void setupSecurity() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String leftPad(String text, int len, char value) {
        StringBuilder sb = new StringBuilder();
        if (text.length() < len) {
            sb.append(String.valueOf(value).repeat(len - text.length()));
            sb.append(text);
            return sb.toString();
        }
        return text;
    }

    public static String rightPad(String text, int len, char value) {
        StringBuilder sb = new StringBuilder();
        if (text.length() < len) {
            sb.append(text);
            sb.append(String.valueOf(value).repeat(len - text.length()));
            return sb.toString();
        }
        return text;
    }
}