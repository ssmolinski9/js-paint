package org.misern.handler.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.misern.MainFrame;
import org.misern.shapes.utils.ShapeType;
import org.misern.ui.InputPanel;
import org.misern.ui.PaintPanel;

/**
 * Action handler for changing shape type
 * @version 0.1
 * @since 0.1
 * @see java.awt.event.ActionListener
 */
public class ShapeTypeChangeActionHandler implements ActionListener {

    private final MainFrame frame;
    private final PaintPanel paintPanel;
    private final InputPanel inputPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public ShapeTypeChangeActionHandler(MainFrame frame) {
        this.frame = frame;
        this.paintPanel = frame.getPaintPanel();
        this.inputPanel = frame.getInputPanel();
    }

    /**
     * Handles occurred action by switching actually using shapes
     * @param e action event created by clicking shape type button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String clickedButtonText = ((JButton)e.getSource()).getText();

        frame.setSelectedToolType(null);
        paintPanel.setDrawExtraPoints(false);
        if ("Line".equals(clickedButtonText)) {
            frame.setSelectedShapeType(ShapeType.LINE);
            inputPanel.boldShapesButton(ShapeType.LINE);
        } else if ("Rectangle".equals(clickedButtonText)) {
            frame.setSelectedShapeType(ShapeType.RECTANGLE);
            inputPanel.boldShapesButton(ShapeType.RECTANGLE);
        } else if ("Circle".equals(clickedButtonText)) {
            frame.setSelectedShapeType(ShapeType.CIRCLE);
            inputPanel.boldShapesButton(ShapeType.CIRCLE);
        } else if ("Polygon".equals(clickedButtonText)) {
            paintPanel.setBezierPoints(getBezierPointsLevel());
            frame.setSelectedShapeType(ShapeType.POLYGON);
            inputPanel.boldShapesButton(ShapeType.POLYGON);
        }

        paintPanel.setDrawingShape(null);
        paintPanel.setDrawing(false);
        paintPanel.repaint();
    }

    private int getBezierPointsLevel() {
        String value = JOptionPane.showInputDialog(this.paintPanel, "Points");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(paintPanel,
                    "Enter valid degree",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            return 0;
        }
    }
}
