package org.misern.handler.filters;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import org.misern.MainFrame;
import org.misern.ui.PaintPanel;

/**
 * Action handler for dividing pixels to image
 * @version 0.4
 * @since 0.4
 * @see ActionListener
 */
public class DividePixelsActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public DividePixelsActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Handles occurred action for dividing pixels
     * @param e action event created by clicking on the filter button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = paintPanel.getImage();
        String value = JOptionPane.showInputDialog(this.paintPanel, "Pixels to divide");

        try {
            int converted = Integer.parseInt(value);
            if (converted < 0 || converted > 255) {
                JOptionPane.showMessageDialog(paintPanel, "Wrong value! Should be 0-255", "Error", JOptionPane.ERROR_MESSAGE);
            }

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color color = new Color(image.getRGB(x, y));

                    int redConverted;
                    int greenConverted;
                    int blueConverted;

                    if (converted <= 0) {
                        redConverted = (color.getRed() / converted) % 256;
                        greenConverted = (color.getGreen() / converted) % 256;
                        blueConverted = (color.getBlue() / converted) % 256;
                    } else {
                        redConverted = color.getRed();
                        greenConverted = color.getGreen();
                        blueConverted = color.getBlue();
                    }

                    image.setRGB(x, y, new Color(redConverted, greenConverted, blueConverted).getRGB());
                }
            }

            paintPanel.repaint();
        } catch(NumberFormatException ignored) {}
    }
}
