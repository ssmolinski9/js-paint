package org.misern.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.misern.histogram.Histogram;

public class HistogramUtils {

    public static BufferedImage equalize(BufferedImage image) {
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        int totalPixels = transformedImage.getWidth() * transformedImage.getHeight();

        Histogram oldImageHistogram = new Histogram(image);

        double[] inputHistogramRed = oldImageHistogram.getHistogram().get(Histogram.CHANNELS.RED);
        double[] inputHistogramGreen = oldImageHistogram.getHistogram().get(Histogram.CHANNELS.GREEN);
        double[] inputHistogramBlue = oldImageHistogram.getHistogram().get(Histogram.CHANNELS.BLUE);

        double[] equalizedHistogramRed = new double[256];
        double[] equalizedHistogramGreen = new double[256];
        double[] equalizedHistogramBlue = new double[256];

        for (int i = 0; i < 256; i++) {
            equalizedHistogramRed[i] = calculateLUTValue(i, inputHistogramRed, totalPixels);
            equalizedHistogramGreen[i] = calculateLUTValue(i, inputHistogramGreen, totalPixels);
            equalizedHistogramBlue[i] = calculateLUTValue(i, inputHistogramBlue, totalPixels);
        }

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                int originalRGBValue = image.getRGB(x, y);
                Color originalPixelColor = new Color(originalRGBValue);

                int calculatedRedValue = (int) equalizedHistogramRed[originalPixelColor.getRed()];
                int calculatedGreenValue = (int) equalizedHistogramGreen[originalPixelColor.getGreen()];
                int calculatedBlueValue = (int) equalizedHistogramBlue[originalPixelColor.getBlue()];

                transformedImage.setRGB(x, y, new Color(calculatedRedValue, calculatedGreenValue, calculatedBlueValue).getRGB());
            }
        }

        return transformedImage;
    }

    public static BufferedImage stretching(BufferedImage image) {
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Histogram oldImageHistogram = new Histogram(image);

        double[] inputHistogramRed = oldImageHistogram.getHistogram().get(Histogram.CHANNELS.RED);
        double[] inputHistogramGreen = oldImageHistogram.getHistogram().get(Histogram.CHANNELS.GREEN);
        double[] inputHistogramBlue = oldImageHistogram.getHistogram().get(Histogram.CHANNELS.BLUE);

        int minimumRed = calculateMinimumShade(inputHistogramRed);
        int maximumRed = calculateMaximalShade(inputHistogramRed);

        int minimumGreen = calculateMinimumShade(inputHistogramGreen);
        int maximumGreen = calculateMaximalShade(inputHistogramGreen);

        int minimumBlue = calculateMinimumShade(inputHistogramBlue);
        int maximumBlue = calculateMaximalShade(inputHistogramBlue);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int actualRGB = image.getRGB(x, y);
                Color actualColor = new Color(actualRGB);

                float red = actualColor.getRed();
                double modifiedRed = ((red - minimumRed) / (maximumRed - minimumRed)) * 255;

                float green = actualColor.getGreen();
                double modifiedGreen = ((green - minimumGreen) / (maximumGreen - minimumGreen)) * 255;

                float blue = actualColor.getBlue();
                double modifiedBlue = (blue - minimumBlue) / (maximumBlue - minimumBlue) * 255;

                transformedImage.setRGB(
                        x,
                        y,
                        new Color(
                                (int) Math.round(modifiedRed),
                                (int) Math.round(modifiedGreen),
                                (int) Math.round(modifiedBlue)).getRGB()
                );
            }
        }

        return transformedImage;
    }

    public static BufferedImage brighten(BufferedImage image) {
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int actualRGB = image.getRGB(x, y);
                Color actualColor = new Color(actualRGB);

                double brightenRed = (45 * Math.log(actualColor.getRed() + 1));
                double brightenGreen = (45 * Math.log(actualColor.getGreen() + 1));
                double brightenBlue = (45 * Math.log(actualColor.getBlue() + 1));

                brightenRed = Math.round(brightenRed);
                brightenGreen = Math.round(brightenGreen);
                brightenBlue = Math.round(brightenBlue);

                if (brightenRed > 255) brightenRed = 255;
                if (brightenGreen > 255) brightenGreen = 255;
                if (brightenBlue > 255) brightenBlue = 255;

                transformedImage.setRGB(
                        x,
                        y,
                        new Color(
                                (int) brightenRed,
                                (int) brightenGreen,
                                (int) brightenBlue).getRGB()
                );
            }
        }

        return transformedImage;
    }

    public static BufferedImage darken(BufferedImage image) {
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int actualRGB = image.getRGB(x, y);
                Color actualColor = new Color(actualRGB);

                double darkenRed = (0.002 * Math.pow(actualColor.getRed(), 2));
                double darkenGreen = (0.002 * Math.pow(actualColor.getGreen(), 2));
                double darkenBlue = (0.002 * Math.pow(actualColor.getBlue(), 2));

                darkenRed = Math.round(darkenRed);
                darkenGreen = Math.round(darkenGreen);
                darkenBlue = Math.round(darkenBlue);

                if (darkenRed > 255) darkenRed = 255;
                if (darkenGreen > 255) darkenGreen = 255;
                if (darkenBlue > 255) darkenBlue = 255;

                transformedImage.setRGB(
                        x,
                        y,
                        new Color(
                                (int) darkenRed,
                                (int) darkenGreen,
                                (int) darkenBlue).getRGB()
                );
            }
        }

        return transformedImage;
    }

    private static int calculateMinimumShade(double [] histogram) {
        int minimum = -1;
        for (int i = 0; i < 256; i++) {
            if (histogram[i] != 0) {
                minimum = i;
                break;
            }
        }

        return minimum;
    }

    private static int calculateMaximalShade(double [] histogram) {
        int maximum = -1;
        for (int i = 255; i >= 0; i--) {
            if (histogram[i] != 0) {
                maximum = i;
                break;
            }
        }

        return maximum;
    }

    private static double calculateLUTValue(int i, double[] histogram, int totalPixels) {
        double val = (calculateCumulativeDistribution(i, histogram, totalPixels) -
                calculateCumulativeDistribution(0, histogram, totalPixels)) / (1 - calculateCumulativeDistribution(0, histogram, totalPixels));
        val = val * (255);

        return val;
    }

    private static double calculateCumulativeDistribution(int k, double[] values, int pixels) {
        float value = 0;
        for (int i = 0; i <= k; i++) {
            value += (values[i] / pixels);
        }

        return value;
    }
}
