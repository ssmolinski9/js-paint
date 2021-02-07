package org.misern.handler.binary;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.ui.BinarizationFrame;

public class GrayscaleConvertHandler implements ActionListener {

    private final BinarizationFrame binarizationFrame;
    private final Color color;

    public GrayscaleConvertHandler(BinarizationFrame binarizationFrame, Color color) {
        this.binarizationFrame = binarizationFrame;
        this.color = color;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = binarizationFrame.getOriginalImage();
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < transformedImage.getWidth(); x++) {
            for (int y = 0; y < transformedImage.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));

                int colorValue = 0;
                if (color == Color.RED) {
                    colorValue = pixelColor.getRed();
                } else if (color == Color.BLUE) {
                    colorValue = pixelColor.getBlue();
                } else if (color == Color.GREEN) {
                    colorValue = pixelColor.getGreen();
                } else if (color == Color.GRAY) {
                    colorValue = (pixelColor.getRed() + pixelColor.getBlue() + pixelColor.getGreen()) / 3;
                }

                transformedImage.setRGB(x, y, new Color(colorValue, colorValue, colorValue).getRGB());
            }
        }

        binarizationFrame.setGrayscaleImage(transformedImage);
        binarizationFrame.getImagePanel().setImage(transformedImage);
        binarizationFrame.getImagePanel().repaint();
    }
}
