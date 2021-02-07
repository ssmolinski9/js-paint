package org.misern.handler.general;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import org.misern.MainFrame;

/**
 * Action handler for printing indicated point coords
 * @version 0.1
 * @since 0.1
 * @see java.awt.event.MouseAdapter
 */
public class CoordsInformationActionHandler extends MouseAdapter {

    private final JLabel coords;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public CoordsInformationActionHandler(MainFrame frame) {
        this.coords = frame.getCoords();
    }

    /**
     * Handles occurred action by updates coords label information
     * @param e action event created by moving cursor on the panel
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        Point selectedPoint = e.getPoint();
        coords.setText(String.format("X: %d, Y: %d", selectedPoint.x, selectedPoint.y));
    }

    /**
     * Handles occurred action by switching actually using shapes
     * @param e action event created by dragging cursor on the panel
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point selectedPoint = e.getPoint();
        coords.setText(String.format("X: %d, Y: %d", selectedPoint.x, selectedPoint.y));
    }
}
