package de.dhbw.karlsruhe.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class BufferedImageHelper {
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage delta(BufferedImage first, BufferedImage second) {
        BufferedImage delta = new BufferedImage(first.getWidth(), first.getHeight(), first.getType());

        int black = Color.black.getRGB();
        int white = Color.white.getRGB();

        for (int y = 0; y < first.getHeight(); y++) {
            for (int x = 0; x < first.getWidth(); x++) {
                if (first.getRGB(x, y) == second.getRGB(x, y)) {
                    delta.setRGB(x, y, black);
                } else {
                    delta.setRGB(x, y, white);
                }
            }
        }

        return delta;
    }

    public static void printDelta(BufferedImage first, BufferedImage second) {
        for (int y = 0; y < Math.min(100, first.getHeight()); y++) {
            for (int x = 0; x < Math.min(100, first.getWidth()); x++) {
                if (first.getRGB(x, y) == second.getRGB(x, y)) {
                    System.out.print(" ");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }

    public static void printNonBlack(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getRGB(x, y) == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }
}
