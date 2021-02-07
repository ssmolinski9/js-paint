package org.misern.handler.filters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.MainFrame;
import org.misern.filter.GeneralFilter;
import org.misern.ui.PaintPanel;

/**
 * Action handler for image equalization
 * @version 0.4
 * @since 0.4
 * @see ActionListener
 */
public class EqualizeFilterActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public EqualizeFilterActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Handles occurred action for image equalization
     * @param e action event created by clicking on the filter button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image;

        double [][] mask = new double[3][3];

        mask[0][0] = 1/16.0;
        mask[0][1] = 2/16.0;
        mask[0][2] = 1/16.0;

        mask[1][0] = 2/16.0;
        mask[1][1] = 4/16.0;
        mask[1][2] = 2/16.0;

        mask[2][0] = 1/16.0;
        mask[2][1] = 2/16.0;
        mask[2][2] = 1/16.0;

        GeneralFilter filter = new GeneralFilter();
        image = filter.filter(paintPanel.getImage(), mask);

        paintPanel.setImage(image);
        paintPanel.repaint();
    }
}
