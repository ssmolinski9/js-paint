package org.misern.handler.filters;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.MainFrame;
import org.misern.ui.PaintPanel;

/**
 * Action handler for darken image
 * @version 0.4
 * @since 0.4
 * @see ActionListener
 */
public class DarkenImageActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public DarkenImageActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Handles occurred action for darkening image
     * @param e action event created by clicking on the filter button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = paintPanel.getImage();

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

                image.setRGB(
                        x,
                        y,
                        new Color(
                                (int) darkenRed,
                                (int) darkenGreen,
                                (int) darkenBlue).getRGB()
                );
            }
        }

        paintPanel.repaint();
    }
}
