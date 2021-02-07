package org.misern.handler.drawing;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import org.misern.MainFrame;
import org.misern.shapes.Shape;
import org.misern.shapes.utils.ShapeFactory;
import org.misern.ui.dialog.CustomDialog;
import org.misern.ui.dialog.DialogFactory;

/**
 * Action handler for inserting new shape
 * @version 0.1
 * @since 0.1
 * @see java.awt.event.ActionListener
 */
public class InsertShapeActionHandler implements ActionListener {

    private final MainFrame frame;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public InsertShapeActionHandler(MainFrame frame) {
        this.frame = frame;
    }

    /**
     * Creates custom dialog for inserting new shape information and creates it
     * @param sourceEvent event information created by clicking JMenuItem
     */
    @Override
    public void actionPerformed(ActionEvent sourceEvent) {
        JMenuItem source = (JMenuItem) sourceEvent.getSource();

        CustomDialog dialog = DialogFactory.createDialog(frame, source.getText());
        dialog.getAccept().addActionListener(acceptEvent -> {
            dialog.dispose();
            insertShapeHandle(dialog, source.getText());
        });

        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void insertShapeHandle(CustomDialog dialog, String type) {
        int x0 = dialog.getClearStartPointXValue();
        int y0 = dialog.getClearStartPointYValue();

        int x1 = dialog.getClearEndPointXValue();
        int y1 = dialog.getClearEndPointYValue();

        Shape shape = ShapeFactory.createShape(type, new Point(x0, y0));
        if (shape != null) {
            shape.resize(new Point(x1, y1));
            frame.getPaintPanel().addShape(shape);
            frame.getPaintPanel().repaint();
        }
    }
}
