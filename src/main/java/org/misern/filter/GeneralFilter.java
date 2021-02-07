package org.misern.filter;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;

/**
 * General image filter for given mask
 * @version 0.4
 * @since 0.4
 */
@NoArgsConstructor
public class GeneralFilter {
    public BufferedImage filter(BufferedImage image, double[][] mask) {
        BufferedImage filtered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                filtered.setRGB(x, y, getCalculatedValue(image, x, y, createMatrix(mask)));
            }
        }

        return filtered;
    }

    public BufferedImage filter(BufferedImage image, double[][] mask, int size) {
        BufferedImage filtered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                filtered.setRGB(x, y, getCalculatedValue(image, x, y, createMatrix(mask, size)));
            }
        }

        return filtered;
    }

    private Map<Point, Double> createMatrix(double[][] mask) {
        Map<Point, Double> result = new HashMap<>();
        result.put(new Point(-1, -1), mask[0][0]);
        result.put(new Point(0, -1), mask[0][1]);
        result.put(new Point(1, -1), mask[0][2]);
        result.put(new Point(-1, 0), mask[1][0]);
        result.put(new Point(0, 0), mask[1][1]);
        result.put(new Point(1, 0), mask[1][2]);
        result.put(new Point(-1, 1), mask[2][0]);
        result.put(new Point(0, 1), mask[2][1]);
        result.put(new Point(1, 1), mask[2][2]);
        return result;
    }

    private Map<Point, Double> createMatrix(double[][] mask, int size) {
        Map<Point, Double> result = new HashMap<>();
        for (int i = -size/2; i <= size/2; i++) {
            for (int j = -size/2; j <= size/2; j++) {
                result.put(new Point(i, j), mask[i+size/2][j+size/2]);
            }
        }
        return result;
    }

    private int getCalculatedValue(BufferedImage image, int x, int y, Map<Point, Double> mask) {
        double sumRed = 0, sumGreen = 0, sumBlue = 0;
        double maskSum = 0.0;

        for(int i = -(int)Math.sqrt(mask.size())/2; i <= (int)Math.sqrt(mask.size())/2; i++) {
            for(int j = -(int)Math.sqrt(mask.size())/2; j <= (int)Math.sqrt(mask.size())/2; j++) {
                if (x + i >= 0 && x + i < image.getWidth() && y + j >= 0 && y + j < image.getHeight()) {
                    maskSum += mask.get(new Point(i, j));

                    Color c = new Color(image.getRGB(x + i, y + j));
                    sumRed += c.getRed() * mask.get(new Point(i, j));
                    sumBlue += c.getBlue() * mask.get(new Point(i, j));
                    sumGreen += c.getGreen() * mask.get(new Point(i, j));
                }
            }
        }

        if (maskSum != 0) {
            sumRed = sumRed / maskSum;
            sumGreen = sumGreen / maskSum;
            sumBlue = sumBlue / maskSum;
        }

        if (sumRed > 255) sumRed = 255;
        if (sumBlue > 255) sumBlue = 255;
        if (sumGreen > 255) sumGreen = 255;

        if (sumRed < 0) sumRed = 0;
        if (sumBlue < 0) sumBlue = 0;
        if (sumGreen < 0) sumGreen = 0;

        Color c = new Color((int) sumRed, (int) sumGreen, (int) sumBlue);
        return c.getRGB();
    }
}
