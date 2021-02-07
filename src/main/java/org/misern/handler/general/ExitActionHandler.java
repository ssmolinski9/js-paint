package org.misern.handler.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.misern.MainFrame;

/**
 * Action handler for exiting an application
 * @version 0.1
 * @since 0.1
 * @see java.awt.event.ActionListener
 */
public class ExitActionHandler implements ActionListener {

    private final MainFrame frame;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public ExitActionHandler(MainFrame frame) {
        this.frame = frame;
    }

    /**
     * Handles occurred action by dispose frame and exit an application
     * @param e action event created by clicking on the exit button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
        System.exit(0);
    }
}
