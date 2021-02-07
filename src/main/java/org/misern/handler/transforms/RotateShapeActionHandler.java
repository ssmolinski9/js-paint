package org.misern.handler.transforms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.misern.MainFrame;
import org.misern.shapes.Shape;
import org.misern.shapes.interfaces.Movable;
import org.misern.ui.PaintPanel;
import org.misern.ui.dialog.CustomDialog;

public class RotateShapeActionHandler implements ActionListener {

    private final MainFrame frame;
    private final PaintPanel paintPanel;

    public RotateShapeActionHandler(MainFrame frame) {
        this.frame = frame;
        this.paintPanel = frame.getPaintPanel();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Shape detectedShape = paintPanel.getSelectedShape();
        if (detectedShape != null) {
            CustomDialog dialog = new CustomDialog(frame, false);
            dialog.createRotateDialog(frame);

            dialog.setTitle("Rotation");
            dialog.getAccept().addActionListener(acceptEvent -> {
                dialog.dispose();
                int x0 = dialog.getClearStartPointXValue();
                int y0 = dialog.getClearStartPointYValue();
                double angle = dialog.getClearAngleValue();

                ((Movable) detectedShape).rotate(x0, y0, angle);
                paintPanel.repaint();
            });

            dialog.setModal(true);
            dialog.setVisible(true);
        }
    }
}
