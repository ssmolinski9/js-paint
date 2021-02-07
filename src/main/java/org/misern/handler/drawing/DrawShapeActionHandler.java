package org.misern.handler.drawing;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.misern.MainFrame;
import org.misern.shapes.Shape;
import org.misern.shapes.Polygon;
import org.misern.shapes.utils.ShapeFactory;
import org.misern.shapes.utils.ShapeType;
import org.misern.ui.PaintPanel;

/**
 * Action handler for drawing new shape
 * @version 0.1
 * @since 0.1
 * @see java.awt.event.MouseAdapter
 */
public class DrawShapeActionHandler extends MouseAdapter {

    private final MainFrame frame;
    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public DrawShapeActionHandler(MainFrame frame) {
        this.frame = frame;
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Dragging action when drawing new shape extends creating shape
     * @param e mouse event created by dragging mouse through source panel
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (paintPanel.isDrawing() && paintPanel.getDrawingShape() != null) {
            draw(e.getPoint());
        }
    }

    /**
     * Pressed mouse action when drawing new shape starts creating shape or add point to polygon
     * @param e mouse event created by clicking mouse inside source panel
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (MouseEvent.BUTTON1 == e.getButton()) {
            if (frame.getSelectedShapeType() != ShapeType.POLYGON) {
                drawSimpleShape(e.getPoint());
            } else {
                if (!paintPanel.isDrawing()) {
                    drawPolygonShape();
                }

                drawPolygonPoint(e.getPoint());
            }
        }

        if (MouseEvent.BUTTON3 == e.getButton() && !ShapeType.POLYGON.equals(frame.getSelectedShapeType())) {
            if (paintPanel.isDrawing() && paintPanel.getDrawingShape() != null) {
                cancelDrawing();
            }
        }
    }

    /**
     * Mouse release action when drawing new shape by finish drawing
     * @param e mouse event created by releasing mouse in source panel
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (frame.getSelectedShapeType() != ShapeType.POLYGON) {
            stopDrawing();
        }
    }

    private void draw(Point dPoint) {
        paintPanel.getDrawingShape().resize(dPoint);
        paintPanel.repaint();
    }

    private void drawSimpleShape(Point startPoint) {
        paintPanel.setDrawing(true);
        Shape shape = ShapeFactory.createShape(frame.getSelectedShapeType(), startPoint);
        if (shape != null) {
            paintPanel.addShape(shape);
            paintPanel.setDrawingShape(shape);
        }
    }

    private void drawPolygonShape() {
        paintPanel.setDrawing(true);

        Polygon polygon = new Polygon(paintPanel.getBezierPoints());
        paintPanel.addShape(polygon);
        paintPanel.setDrawingShape(polygon);
    }

    private void drawPolygonPoint(Point startPoint) {
        ((Polygon)paintPanel.getDrawingShape()).addPoint(startPoint);
        paintPanel.repaint();
    }

    private void cancelDrawing() {
        stopDrawing();
        paintPanel.removeLastShape();
    }

    private void stopDrawing() {
        paintPanel.setDrawing(false);
        paintPanel.setDrawingShape(null);
    }
}
