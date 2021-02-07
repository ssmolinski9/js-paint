package org.misern.handler.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.misern.MainFrame;
import org.misern.ui.PaintPanel;

/**
 * Action handler for creating new file
 * @version 0.1
 * @since 0.1
 * @see java.awt.event.ActionListener
 */
public class NewFileActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public NewFileActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Handles occurred action by clear paint panel
     * @param e action event created by clicking on the new file button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        paintPanel.clearPaintPanel();
        paintPanel.repaint();
    }
}
