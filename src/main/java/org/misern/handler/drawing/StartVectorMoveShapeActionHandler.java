package org.misern.handler.drawing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.misern.MainFrame;
import org.misern.ui.PaintPanel;

public class StartVectorMoveShapeActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    public StartVectorMoveShapeActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.paintPanel.setMovingVector(true);
    }
}
