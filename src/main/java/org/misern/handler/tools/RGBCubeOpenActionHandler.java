package org.misern.handler.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import org.misern.MainFrame;
import org.misern.ui.PaintPanel;
import org.misern.ui.colors.RGBCubePanel;

/**
 * Action handler for opening colors rgb cube tool
 * @version 0.3
 * @since 0.3
 * @see ActionListener
 */
public class RGBCubeOpenActionHandler implements ActionListener {

    private final MainFrame frame;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public RGBCubeOpenActionHandler(MainFrame frame) {
        this.frame = frame;

        PaintPanel paintPanel = frame.getPaintPanel();
    }

    /**
     * Handles occurred action by clicking on cube button
     * @param e action event created by clicking cube button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog dialog = new JDialog(frame);
        dialog.setSize(500, 350);
        dialog.setTitle("RGB Cube");

        RGBCubePanel rgbCubePanel = new RGBCubePanel();
        dialog.add(rgbCubePanel);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
