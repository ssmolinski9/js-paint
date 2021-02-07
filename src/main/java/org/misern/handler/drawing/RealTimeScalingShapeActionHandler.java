package org.misern.handler.drawing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.misern.MainFrame;
import org.misern.shapes.Polygon;
import org.misern.shapes.interfaces.Movable;
import org.misern.ui.PaintPanel;

public class RealTimeScalingShapeActionHandler extends MouseAdapter {

    private final PaintPanel paintPanel;

    public RealTimeScalingShapeActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paintPanel.isScaling() && paintPanel.getScalingStartPoint() != null) {
            paintPanel.setScalingActualPoint(e.getPoint());
            paintPanel.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paintPanel.isScaling()) {
            if (MouseEvent.BUTTON1 == e.getButton()) {
                if (paintPanel.getScalingStartPoint() == null) {
                    paintPanel.setScalingStartPoint(e.getPoint());
                } else {
                    double dx, dy;
                    if (paintPanel.getSelectedShape() instanceof Polygon) {
                        dx = Math.abs(((Polygon) paintPanel.getSelectedShape()).getPoints().get(0).x - paintPanel.getScalingActualPoint().x) * 0.01;
                        dy = Math.abs(((Polygon) paintPanel.getSelectedShape()).getPoints().get(0).y - paintPanel.getScalingActualPoint().y) * 0.01;
                    } else {
                        dx = Math.abs(paintPanel.getSelectedShape().getStart().x - paintPanel.getScalingActualPoint().x);
                        dy = Math.abs(paintPanel.getSelectedShape().getStart().y - paintPanel.getScalingActualPoint().y);
                    }

                    ((Movable)paintPanel.getSelectedShape()).scale(paintPanel.getScalingStartPoint().x, paintPanel.getScalingStartPoint().y, dx, dy);
                    stopScaling();
                }
            }

            if (MouseEvent.BUTTON3 == e.getButton()) {
                stopScaling();
            }
        }
    }

    private void stopScaling() {
        paintPanel.setScaling(false);
        paintPanel.setScalingStartPoint(null);
        paintPanel.setScalingActualPoint(null);
        paintPanel.repaint();
    }
}
