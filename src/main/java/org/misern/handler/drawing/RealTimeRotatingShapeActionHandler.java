package org.misern.handler.drawing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.misern.MainFrame;
import org.misern.shapes.Polygon;
import org.misern.shapes.interfaces.Movable;
import org.misern.ui.PaintPanel;
import org.misern.utils.MathUtils;

public class RealTimeRotatingShapeActionHandler extends MouseAdapter {

    private final PaintPanel paintPanel;

    public RealTimeRotatingShapeActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paintPanel.isRotating() && paintPanel.getRotatingStartPoint() != null) {
            paintPanel.setRotatingActualPoint(e.getPoint());
            paintPanel.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paintPanel.isRotating()) {
            if (MouseEvent.BUTTON1 == e.getButton()) {
                if (paintPanel.getRotatingStartPoint() == null) {
                    paintPanel.setRotatingStartPoint(e.getPoint());
                } else {
                    double angle;
                    if (paintPanel.getSelectedShape() instanceof Polygon) {
                        angle = MathUtils.getPointsAngle(((Polygon) paintPanel.getSelectedShape()).getPoints().get(0), paintPanel.getRotatingActualPoint());
                    } else {
                        angle = MathUtils.getPointsAngle(paintPanel.getSelectedShape().getStart(), paintPanel.getRotatingActualPoint());
                    }
                    ((Movable)paintPanel.getSelectedShape()).rotate(paintPanel.getRotatingStartPoint().x, paintPanel.getRotatingStartPoint().y, angle);
                    stopRotating();
                }
            }

            if (MouseEvent.BUTTON3 == e.getButton()) {
                stopRotating();
            }
        }
    }

    private void stopRotating() {
        paintPanel.setRotating(false);
        paintPanel.setRotatingStartPoint(null);
        paintPanel.setRotatingActualPoint(null);
        paintPanel.repaint();
    }
}
