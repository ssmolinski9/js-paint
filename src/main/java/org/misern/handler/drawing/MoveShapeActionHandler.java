package org.misern.handler.drawing;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.misern.MainFrame;
import org.misern.shapes.interfaces.Movable;
import org.misern.tools.ToolType;
import org.misern.ui.PaintPanel;

/**
 * Action handler for moving existing shape
 * @version 0.1
 * @since 0.1
 * @see java.awt.event.MouseAdapter
 */
public class MoveShapeActionHandler extends MouseAdapter {

    private final MainFrame frame;
    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public MoveShapeActionHandler(MainFrame frame) {
        this.frame = frame;
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Dragging action when moving clicked shape
     * @param e mouse event created by dragging mouse through source panel
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (paintPanel.isMoving()) {
            move(e.getPoint());
        }
    }

    /**
     * Pressed mouse action when moving shape starts
     * @param e mouse event created by clicking mouse inside source panel
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (ToolType.MOVE.equals(frame.getSelectedToolType())) {
            if (MouseEvent.BUTTON1 == e.getButton()) {
                startMoving(e.getPoint());
            }
        }
    }

    /**
     * Mouse release action when moving by finish moving
     * @param e mouse event created by releasing mouse in source panel
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (ToolType.MOVE.equals(frame.getSelectedToolType())) {
            stopMoving();
        }
    }

    private void startMoving(Point startPoint) {
        Movable detectedShape = paintPanel.getDetectedShape(startPoint);
        if (detectedShape != null) {
            paintPanel.setMovingShape(detectedShape);
            paintPanel.setMoving(true);
            paintPanel.setMovingStartPoint(startPoint);
        }
    }

    private void move(Point point) {
        int dx = point.x - paintPanel.getMovingStartPoint().x;
        int dy = point.y - paintPanel.getMovingStartPoint().y;

        paintPanel.setMovingStartPoint(point);
        paintPanel.getMovingShape().move(dx, dy);
        paintPanel.repaint();
    }

    private void stopMoving() {
        paintPanel.setMovingShape(null);
        paintPanel.setMoving(false);
    }
}
