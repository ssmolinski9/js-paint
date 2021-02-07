package org.misern.handler.drawing;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.misern.MainFrame;
import org.misern.shapes.Shape;
import org.misern.tools.ToolType;
import org.misern.ui.PaintPanel;

public class SelectShapeActionHandler extends MouseAdapter {

    private final MainFrame frame;
    private final PaintPanel paintPanel;

    public SelectShapeActionHandler(MainFrame frame) {
        this.frame = frame;
        this.paintPanel = frame.getPaintPanel();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (ToolType.SELECT.equals(frame.getSelectedToolType())) {
            if (MouseEvent.BUTTON1 == e.getButton()) {
                handleShapes(e.getPoint());
            }
        }
    }

    private void handleShapes(Point selectedPoint) {
        if (paintPanel.isRotating() || paintPanel.isScaling()) {
            return;
        }

        Shape detectedShape = (Shape) paintPanel.getDetectedShape(selectedPoint);
        if (detectedShape == null) {
            detectedShape = (Shape) paintPanel.getDetectedPoint(selectedPoint);
        }

        if (paintPanel.getSelectedShape() != null) {
            paintPanel.getSelectedShape().setSelected(false);
        }

        if (detectedShape != null) {
            detectedShape.setSelected(true);
            frame.getInputPanel().getMoveVec().setEnabled(true);
            frame.getInputPanel().getRotate().setEnabled(true);
            frame.getInputPanel().getScale().setEnabled(true);
        } else {
            frame.getInputPanel().getMoveVec().setEnabled(false);
            frame.getInputPanel().getRotate().setEnabled(false);
            frame.getInputPanel().getScale().setEnabled(false);
            paintPanel.setMovingVector(false);
            paintPanel.setMovingVectorPoint(null);

            paintPanel.setRotating(false);
            paintPanel.setRotatingStartPoint(null);
            paintPanel.setRotatingActualPoint(null);

            paintPanel.setScaling(false);
            paintPanel.setScalingStartPoint(null);
            paintPanel.setScalingActualPoint(null);
        }

        paintPanel.setSelectedShape(detectedShape);
        paintPanel.repaint();
    }
}
