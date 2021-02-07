package org.misern.handler.transforms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.misern.MainFrame;
import org.misern.shapes.Shape;
import org.misern.shapes.interfaces.Movable;
import org.misern.ui.PaintPanel;
import org.misern.ui.dialog.CustomDialog;

public class ScaleShapeActionHandler implements ActionListener {

    private final MainFrame frame;
    private final PaintPanel paintPanel;

    public ScaleShapeActionHandler(MainFrame frame) {
        this.frame = frame;
        this.paintPanel = frame.getPaintPanel();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Shape detectedShape = paintPanel.getSelectedShape();
        if (detectedShape != null) {
            CustomDialog dialog = new CustomDialog(frame, false);
            dialog.createScaleDialog(frame);

            dialog.setTitle("Scaling");
            dialog.getAccept().addActionListener(acceptEvent -> {
                dialog.dispose();
                int x0 = dialog.getClearStartPointXValue();
                int y0 = dialog.getClearStartPointYValue();

                double scaleX = dialog.getClearScaleXValue();
                double scaleY = dialog.getClearScaleYValue();

                ((Movable) detectedShape).scale(x0, y0, scaleX, scaleY);
                paintPanel.repaint();
            });

            dialog.setModal(true);
            dialog.setVisible(true);
        }
    }
}
