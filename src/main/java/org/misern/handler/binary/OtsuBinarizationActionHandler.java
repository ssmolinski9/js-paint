package org.misern.handler.binary;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.ui.BinarizationFrame;
import org.misern.utils.MathUtils;

public class OtsuBinarizationActionHandler implements ActionListener {

    private final BinarizationFrame binarizationFrame;

    public OtsuBinarizationActionHandler(BinarizationFrame binarizationFrame) {
        this.binarizationFrame = binarizationFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        binarizationFrame.getConfirmManuallyBinarization().setEnabled(false);
        binarizationFrame.getBinarizationLevel().setEnabled(false);

        BufferedImage image = binarizationFrame.getGrayscaleImage();
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Integer level = MathUtils.getOtsuThreshold(image);

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
