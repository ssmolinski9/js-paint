package org.misern.handler.drawing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.misern.MainFrame;
import org.misern.shapes.Polygon;
import org.misern.shapes.Shape;
import org.misern.shapes.interfaces.Movable;
import org.misern.ui.PaintPanel;

public class RealTimeVectorMoveShapeActionHandler extends MouseAdapter {

    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public RealTimeVectorMoveShapeActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paintPanel.isMovingVector()) {
            paintPanel.setMovingVectorPoint(e.getPoint());
            paintPanel.repaint();
        }
    }

    /**
     * Pressed mouse action when drawing new shape starts creating shape or add point to polygon
     * @param e mouse event created by clicking mouse inside source panel
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (paintPanel.isMovingVector()) {
            if (MouseEvent.BUTTON1 == e.getButton()) {
                Movable selectedShape = (Movable) paintPanel.getSelectedShape();
                if (selectedShape instanceof Polygon) {
                    selectedShape.move(paintPanel.getMovingVectorPoint().x - ((Polygon) selectedShape).getPoints().get(0).x,
                            paintPanel.getMovingVectorPoint().y - ((Polygon) selectedShape).getPoints().get(0).y);
                } else {
                    selectedShape.move(paintPanel.getMovingVectorPoint().x - ((Shape) selectedShape).getStart().x,
                            paintPanel.getMovingVectorPoint().y - ((Shape) selectedShape).getStart().y);
                }

                stopDrawing();
            }

            if (MouseEvent.BUTTON3 == e.getButton()) {
                stopDrawing();
            }
        }
    }

    private void stopDrawing() {
        paintPanel.setMovingVectorPoint(null);
        paintPanel.setMovingVector(false);
        paintPanel.repaint();
    }
}
