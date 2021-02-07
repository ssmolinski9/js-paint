package org.misern.handler.filters;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.MainFrame;
import org.misern.ui.PaintPanel;

/**
 * Action handler for converting image to grayscale
 * @version 0.4
 * @since 0.4
 * @see ActionListener
 */
public class GrayscaleImageActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public GrayscaleImageActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Handles occurred action for converting image to grayscale
     * @param e action event created by clicking on the filter button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = paintPanel.getImage();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int actualRGB = image.getRGB(x, y);
                Color actualColor = new Color(actualRGB);

                int equalized = (actualColor.getRed() + actualColor.getGreen() + actualColor.getBlue()) / 3;

                image.setRGB(x, y, new Color(equalized, equalized, equalized).getRGB());
            }
        }

        paintPanel.repaint();
    }
}
