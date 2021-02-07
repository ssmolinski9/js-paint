package org.misern.handler.morphology;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.ui.MorphologyFrame;

public class ClosureActionHandler implements ActionListener {

    private final MorphologyFrame morphologyFrame;

    public ClosureActionHandler(MorphologyFrame morphologyFrame) {
        this.morphologyFrame = morphologyFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = morphologyFrame.getGrayscaleImage();
        BufferedImage dilatationImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (image.getRGB(x, y) == Color.BLACK.getRGB()) {
                    // 3x3 mask
                    boolean whitePixelDetected = false;
                    for(int my = y - 1; my <= y + 1 && !whitePixelDetected; my++){
                        for(int mx = x - 1; mx <= x + 1 && !whitePixelDetected; mx++){
                            if(my >= 0 && my < height && mx >= 0 && mx < width) {
                                if(image.getRGB(mx, my) != Color.BLACK.getRGB()) {
                                    whitePixelDetected = true;
                                    dilatationImage.setRGB(x, y, Color.WHITE.getRGB());
                                }
                            }
                        }
                    }

                    if(!whitePixelDetected){
                        dilatationImage.setRGB(x, y, Color.BLACK.getRGB());
                    }
                } else {
                    dilatationImage.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        BufferedImage transformedImage = new BufferedImage(dilatationImage.getWidth(), dilatationImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (dilatationImage.getRGB(x, y) == Color.WHITE.getRGB()) {
                    // 3x3 mask
                    boolean whitePixelDetected = false;
                    for(int my = y - 1; my <= y + 1 && !whitePixelDetected; my++){
                        for(int mx = x - 1; mx <= x + 1 && !whitePixelDetected; mx++){
                            if(my >= 0 && my < height && mx >= 0 && mx < width) {
                                if(dilatationImage.getRGB(mx, my) != Color.WHITE.getRGB()) {
                                    whitePixelDetected = true;
                                    transformedImage.setRGB(x, y, Color.BLACK.getRGB());
                                }
                            }
                        }
                    }

                    if(!whitePixelDetected){
                        transformedImage.setRGB(x, y, Color.WHITE.getRGB());
                    }
                } else {
                    transformedImage.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        morphologyFrame.getImagePanel().setImage(transformedImage);
        morphologyFrame.getImagePanel().repaint();
    }
}
