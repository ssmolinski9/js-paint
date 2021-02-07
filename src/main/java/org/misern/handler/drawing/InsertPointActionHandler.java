package org.misern.handler.drawing;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import org.misern.MainFrame;
import org.misern.shapes.Polygon;
import org.misern.shapes.utils.ShapeType;
import org.misern.ui.dialog.CustomDialog;

/**
 * Action handler for inserting new shape
 * @version 0.1
 * @since 0.1
 * @see ActionListener
 */
public class InsertPointActionHandler implements ActionListener {

    private final MainFrame frame;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public InsertPointActionHandler(MainFrame frame) {
        this.frame = frame;
    }

    /**
     * Creates custom dialog for inserting new shape information and creates it
     * @param sourceEvent event information created by clicking JMenuItem
     */
    @Override
    public void actionPerformed(ActionEvent sourceEvent) {
        if (frame.getSelectedShapeType().equals(ShapeType.POLYGON)) {
            if (frame.getPaintPanel().getDrawingShape() == null) {
                drawPolygonShape();
            }

            createDialogForPolygon(new Polygon(frame.getPaintPanel().getBezierPoints()));
        }
    }

    private void createDialogForPolygon(Polygon detectedShape) {
        CustomDialog dialog = new CustomDialog(frame, detectedShape);
        dialog.setTitle("Create point ");
        dialog.getAccept().addActionListener(acceptEvent -> {
            dialog.dispose();
            shapeResizeForPolygonHandle(dialog);
        });

        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void shapeResizeForPolygonHandle(CustomDialog dialog) {
        int x0 = dialog.getClearStartPointXValue();
        int y0 = dialog.getClearStartPointYValue();

        ((Polygon)frame.getPaintPanel().getDrawingShape()).addPoint(new Point(x0, y0));
        frame.getPaintPanel().repaint();
    }

    private void drawPolygonShape() {
        frame.getPaintPanel().setDrawing(true);

        Polygon polygon = new Polygon(frame.getPaintPanel().getBezierPoints());
        frame.getPaintPanel().addShape(polygon);
        frame.getPaintPanel().setDrawingShape(polygon);
    }
}
