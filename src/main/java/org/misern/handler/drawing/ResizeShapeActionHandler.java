package org.misern.handler.drawing;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.misern.MainFrame;
import org.misern.shapes.Polygon;
import org.misern.shapes.Shape;
import org.misern.shapes.interfaces.Drawable;
import org.misern.shapes.utils.ShapeType;
import org.misern.tools.ToolType;
import org.misern.ui.PaintPanel;
import org.misern.ui.dialog.CustomDialog;

/**
 * Action handler for resizing existing shape
 * @version 0.1
 * @since 0.1
 * @see java.awt.event.MouseAdapter
 */
public class ResizeShapeActionHandler extends MouseAdapter {

    private final MainFrame frame;
    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public ResizeShapeActionHandler(MainFrame frame) {
        this.frame = frame;
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Dragging action when resizing clicked shape
     * @param e mouse event created by dragging mouse through source panel
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (paintPanel.isResizing()) {
            resize(e.getPoint());
        }
    }

    /**
     * Pressed mouse action when resizing shape starts or indicates resizing by input panel
     * @param e mouse event created by clicking mouse inside source panel
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (ToolType.RESIZE.equals(frame.getSelectedToolType())) {
            if (MouseEvent.BUTTON1 == e.getButton()) {
                if (e.getClickCount() >= 2) {
                    Shape detectedShape = (Shape) paintPanel.getDetectedPoint(e.getPoint());
                    if (detectedShape != null) {
                        if (ShapeType.POLYGON.equals(detectedShape.getType())) {
                            createDialogForPolygon((Polygon) detectedShape, e.getPoint());
                        } else {
                            createDialog(detectedShape);
                        }
                    }
                } else {
                    startResizing(e.getPoint());
                }
            }
        }
    }

    /**
     * Mouse release action when moving by finish resizing
     * @param e mouse event created by releasing mouse in source panel
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (ToolType.RESIZE.equals(frame.getSelectedToolType())) {
            stopResizing();
        }
    }

    private void createDialog(Shape detectedShape) {
        CustomDialog dialog = new CustomDialog(frame, detectedShape);
        dialog.setTitle("Edit " + detectedShape.getType().name());
        dialog.getAccept().addActionListener(acceptEvent -> {
            dialog.dispose();
            shapeResizeHandle(dialog, detectedShape);
        });

        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void createDialogForPolygon(Polygon detectedShape, Point selectedPoint) {
        CustomDialog dialog = new CustomDialog(frame, detectedShape, selectedPoint);
        dialog.setTitle("Edit " + detectedShape.getType().name());
        dialog.getAccept().addActionListener(acceptEvent -> {
            dialog.dispose();
            shapeResizeForPolygonHandle(dialog, detectedShape, selectedPoint);
        });

        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void shapeResizeHandle(CustomDialog dialog, Shape shape) {
        int x0 = dialog.getClearStartPointXValue();
        int y0 = dialog.getClearStartPointYValue();

        int x1 = dialog.getClearEndPointXValue();
        int y1 = dialog.getClearEndPointYValue();

        shape.updatePoints(x0, y0, x1, y1);
        paintPanel.repaint();
    }

    private void shapeResizeForPolygonHandle(CustomDialog dialog, Polygon shape, Point point) {
        int x0 = dialog.getClearStartPointXValue();
        int y0 = dialog.getClearStartPointYValue();

        shape.updatePoint(point, new Point(x0, y0));
        paintPanel.repaint();
    }

    private void startResizing(Point startPoint) {
        Drawable detectedShape = paintPanel.getDetectedPoint(startPoint);
        if (detectedShape != null) {
            paintPanel.setResizingShape(detectedShape);
            paintPanel.setResizing(true);
        }
    }

    private void resize(Point point) {
        Point newPosition = paintPanel.getResizingShape().resizeByAnyPoint(paintPanel.getResizingStartPoint(), point);
        paintPanel.setResizingStartPoint(newPosition);
        paintPanel.repaint();
    }

    private void stopResizing() {
        paintPanel.setResizingShape(null);
        paintPanel.setResizing(false);
    }
}
