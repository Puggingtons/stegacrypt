package de.dhbw.karlsruhe;

import de.dhbw.karlsruhe.steganography.BasicSteganography;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Security;

import static de.dhbw.karlsruhe.util.StringHelper.toFixedBinary;

public class Main {
    public static void main(String[] args) {
        setupSecurity();

        BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);

        var text = "abcdef".getBytes();

        BasicSteganography basicSteganography = new BasicSteganography();

        System.out.println(basicSteganography.canEncode(text, image));

        BufferedImage result = basicSteganography.encode(text, image);

        byte[] decoded = basicSteganography.decode(result);

        printBytes(text);
        System.out.println();
        printImage(result);

        System.out.println("------------");

        printBytes(text);
        printBytes(decoded);
    }

    private static void setupSecurity() {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static void dings() throws IOException {
        BufferedImage img = ImageIO.read(new File("examples/lenna.png"));

        int bit_per_color = 0b1111_1111;
        int a = bit_per_color << 24;
        int r = bit_per_color << 16;
        int g = bit_per_color << 8;
        int b = bit_per_color;

        System.out.println(toFixedBinary(a, 32));
        System.out.println(toFixedBinary(r, 32));
        System.out.println(toFixedBinary(g, 32));
        System.out.println(toFixedBinary(b, 32));


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

    private static void printBytes(byte[] input) {
        StringBuilder sb = new StringBuilder();
        for (byte b : input) {
            sb.append(toFixedBinary(b, 8));
            sb.append(" ");
        }
        System.out.println(sb);
    }

    private static void printPixel(int pixel) {
        String s = new StringBuilder(toFixedBinary(pixel, 32)).reverse().toString();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 24; i += 8) {
            sb.append(s, i, Math.min(s.length(), i + 8));
            sb.append(" ");
        }

        System.out.println(sb);
    }

    private static void printImage(BufferedImage img) {
        System.out.println("bbbbbbbb gggggggg rrrrrrrr \n");
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                printPixel(img.getRGB(x, y));
            }
        }
    }
}