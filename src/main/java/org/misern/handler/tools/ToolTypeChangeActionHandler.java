package org.misern.handler.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.misern.MainFrame;
import org.misern.tools.ToolType;
import org.misern.ui.InputPanel;
import org.misern.ui.PaintPanel;

/**
 * Action handler for changing tool type
 * @version 0.1
 * @since 0.1
 * @see java.awt.event.ActionListener
 */
public class ToolTypeChangeActionHandler implements ActionListener {

    private final MainFrame frame;
    private final PaintPanel paintPanel;
    private final InputPanel inputPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public ToolTypeChangeActionHandler(MainFrame frame) {
        this.frame = frame;
        this.paintPanel = frame.getPaintPanel();
        this.inputPanel = frame.getInputPanel();
    }

    /**
     * Handles occurred action by switching actually using tool
     * @param e action event created by clicking tool type button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String clickedButtonText = ((JButton)e.getSource()).getText();

        frame.setSelectedShapeType(null);
        if ("Move".equals(clickedButtonText)) {
            frame.setSelectedToolType(ToolType.MOVE);
            inputPanel.boldToolsButton(ToolType.MOVE);
            paintPanel.setResizing(false);
            paintPanel.setDrawExtraPoints(false);
        } else if ("Resize".equals(clickedButtonText)) {
            frame.setSelectedToolType(ToolType.RESIZE);
            inputPanel.boldToolsButton(ToolType.RESIZE);
            paintPanel.setMoving(false);
            paintPanel.setDrawExtraPoints(true);
        } else if ("Select".equals(clickedButtonText)) {
            frame.setSelectedToolType(ToolType.SELECT);
            inputPanel.boldToolsButton(ToolType.SELECT);
            paintPanel.setMoving(false);
            paintPanel.setDrawExtraPoints(false);
        }

        paintPanel.setDrawingShape(null);
        paintPanel.setDrawing(false);
        paintPanel.repaint();
    }
}
