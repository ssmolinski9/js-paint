package org.misern.handler.transforms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.misern.MainFrame;
import org.misern.shapes.Shape;
import org.misern.shapes.interfaces.Movable;
import org.misern.ui.PaintPanel;
import org.misern.ui.dialog.CustomDialog;

public class VectorMoveShapeActionHandler implements ActionListener {

    private final MainFrame frame;
    private final PaintPanel paintPanel;

    public VectorMoveShapeActionHandler(MainFrame frame) {
        this.frame = frame;
        this.paintPanel = frame.getPaintPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Shape detectedShape = paintPanel.getSelectedShape();
        if (detectedShape != null) {
            CustomDialog dialog = new CustomDialog(frame, false);
            dialog.createSimpleDialog(frame);

            dialog.setTitle("Move by vector");
            dialog.getAccept().addActionListener(acceptEvent -> {
                dialog.dispose();

                int x0 = dialog.getClearStartPointXValue();
                int y0 = dialog.getClearStartPointYValue();
                ((Movable)detectedShape).move(x0, y0);
                paintPanel.repaint();
            });

            dialog.setModal(true);
            dialog.setVisible(true);
        }
    }
}
