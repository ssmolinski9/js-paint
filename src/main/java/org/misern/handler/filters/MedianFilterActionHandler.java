package org.misern.handler.filters;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import org.misern.MainFrame;
import org.misern.ui.PaintPanel;

/**
 * Action handler for image median filtering
 * @version 0.4
 * @since 0.4
 * @see ActionListener
 */
public class MedianFilterActionHandler implements ActionListener {

    private final PaintPanel paintPanel;
    private final static int WINDOW_SIZE = 3;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public MedianFilterActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Handles occurred action for image median filtering
     * @param e action event created by clicking on the filter button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = paintPanel.getImage();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x, y, getCalculatedValue(image, x, y));
            }
        }

        paintPanel.repaint();
    }

    private int getCalculatedValue(BufferedImage image, int x, int y) {
        double [] red = new double[WINDOW_SIZE*WINDOW_SIZE];
        double [] green = new double[WINDOW_SIZE*WINDOW_SIZE];
        double [] blue = new double[WINDOW_SIZE*WINDOW_SIZE];

        int counter = 0;
        for(int i = -WINDOW_SIZE / 2; i <= WINDOW_SIZE / 2; i++) {
            for(int j = -WINDOW_SIZE / 2; j <= WINDOW_SIZE / 2; j++) {
                if (x + i >= 0 && x + i < image.getWidth() && y + j >= 0 && y + j < image.getHeight()) {
                    Color c = new Color(image.getRGB(x + i, y + j));
                    red[counter] = c.getRed();
                    green[counter] = c.getGreen();
                    blue[counter] = c.getBlue();

                    counter++;
                }
            }
        }

        Arrays.sort(red);
        Arrays.sort(green);
        Arrays.sort(blue);

        double meanRed = red[(red.length - 1) / 2];
        double meanGreen = green[(green.length - 1) / 2];
        double meanBlue = blue[(blue.length - 1) / 2];

        if (meanRed > 255) meanRed = 255;
        if (meanBlue > 255) meanBlue = 255;
        if (meanGreen > 255) meanGreen = 255;
        if (meanRed < 0) meanRed = 0;
        if (meanBlue < 0) meanBlue = 0;
        if (meanGreen < 0) meanGreen = 0;

        return new Color((int) meanRed, (int) meanGreen, (int) meanBlue).getRGB();
    }
}
