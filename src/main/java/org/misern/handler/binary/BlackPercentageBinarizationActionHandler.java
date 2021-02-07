package org.misern.handler.binary;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.histogram.Histogram;
import org.misern.ui.BinarizationFrame;

public class BlackPercentageBinarizationActionHandler implements ActionListener {

    private final BinarizationFrame binarizationFrame;

    public BlackPercentageBinarizationActionHandler(BinarizationFrame binarizationFrame) {
        this.binarizationFrame = binarizationFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Double level = null;
        try {
            level = Integer.parseInt(binarizationFrame.getPercentageLevel().getText()) / 100.0;
        } catch (NumberFormatException ignored) {}

        if (level == null || level > 100 || level < 0) {
            return;
        }

        BufferedImage image = binarizationFrame.getGrayscaleImage();
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Histogram histogram = new Histogram(image);

        int [] LUT = new int[256];
        double limes = level * (double)(transformedImage.getWidth() * transformedImage.getHeight());

        int nextSum = 0;
        for (int i = 0; i < 256; i++) {
            nextSum = nextSum + (int)histogram.getHistogram().get(Histogram.CHANNELS.RED)[i];
            if (nextSum < limes) {
                LUT[i] = 0;
            } else {
                LUT[i] = 1;
            }
        }

        for (int x = 0; x < transformedImage.getWidth(); x++) {
            for (int y = 0; y < transformedImage.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                int colorValue = pixelColor.getRed();
                if (LUT[colorValue] == 0) {
                    transformedImage.setRGB(x, y, new Color(0, 0, 0).getRGB());
                } else {
                    transformedImage.setRGB(x, y, new Color(255, 255, 255).getRGB());
                }
            }
        }

        binarizationFrame.getImagePanel().setImage(transformedImage);
        binarizationFrame.getImagePanel().repaint();
    }
}
