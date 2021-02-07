package org.misern.handler.binary;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.ui.BinarizationFrame;

public class ManualBinarizationActionHandler implements ActionListener {

    private final BinarizationFrame binarizationFrame;

    public ManualBinarizationActionHandler(BinarizationFrame binarizationFrame) {
        this.binarizationFrame = binarizationFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer level = null;
        try {
            level = Integer.parseInt(binarizationFrame.getBinarizationLevel().getText());
        } catch (NumberFormatException ignored) {}

        if (level == null || level > 255 || level < 0) {
            return;
        }

        BufferedImage image = binarizationFrame.getGrayscaleImage();
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < transformedImage.getWidth(); x++) {
            for (int y = 0; y < transformedImage.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                int colorValue = pixelColor.getRed();
                transformedImage.setRGB(x, y, colorValue > level ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        binarizationFrame.getImagePanel().setImage(transformedImage);
        binarizationFrame.getImagePanel().repaint();
    }
}
