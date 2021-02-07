package org.misern.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import lombok.Getter;
import org.misern.shapes.utils.ShapeType;
import org.misern.tools.ToolType;

/**
 * Main control panel with all application tools controls
 * @version 0.5
 * @since 0.1
 * @see javax.swing.JPanel
 */
@Getter
public class InputPanel extends JPanel {

    private final JButton line = new JButton("Line");
    private final JButton rectangle = new JButton("Rectangle");
    private final JButton circle = new JButton("Circle");
    private final JButton polygon = new JButton("Polygon");
    private final JButton move = new JButton("Move");
    private final JButton resize = new JButton("Resize");
    private final JButton select = new JButton("Select");
    private final JButton color = new JButton("Color");
    private final JButton rgbCube = new JButton("Cube");

    private final JButton moveVec = new JButton("Move vec");
    private final JButton rotate = new JButton("Rotate");
    private final JButton scale = new JButton("Scale");

    public InputPanel() {
        setBackground(Color.LIGHT_GRAY);
        setBorder(new BevelBorder(BevelBorder.RAISED));

        JLabel shapes = new JLabel("Shapes", SwingConstants.CENTER);
        shapes.setFont(new Font("Ubuntu", Font.BOLD, 14));

        shapes.setMaximumSize(rectangle.getPreferredSize());
        shapes.setMinimumSize(rectangle.getPreferredSize());
        shapes.setSize(rectangle.getPreferredSize());

        JLabel tools = new JLabel("Tools", SwingConstants.CENTER);
        tools.setFont(new Font("Ubuntu", Font.BOLD, 14));

        tools.setMaximumSize(rectangle.getPreferredSize());
        tools.setMinimumSize(rectangle.getPreferredSize());
        tools.setSize(rectangle.getPreferredSize());

        line.setMaximumSize(rectangle.getPreferredSize());
        line.setMinimumSize(rectangle.getPreferredSize());

        circle.setMaximumSize(rectangle.getPreferredSize());
        circle.setMinimumSize(rectangle.getPreferredSize());

        polygon.setMaximumSize(rectangle.getPreferredSize());
        polygon.setMinimumSize(rectangle.getPreferredSize());

        move.setMaximumSize(rectangle.getPreferredSize());
        move.setMinimumSize(rectangle.getPreferredSize());

        resize.setMaximumSize(rectangle.getPreferredSize());
        resize.setMinimumSize(rectangle.getPreferredSize());

        select.setMaximumSize(rectangle.getPreferredSize());
        select.setMinimumSize(rectangle.getPreferredSize());

        color.setMaximumSize(rectangle.getPreferredSize());
        color.setMinimumSize(rectangle.getPreferredSize());

        rgbCube.setMaximumSize(rectangle.getPreferredSize());
        rgbCube.setMinimumSize(rectangle.getPreferredSize());

        moveVec.setMaximumSize(rectangle.getPreferredSize());
        moveVec.setMinimumSize(rectangle.getPreferredSize());
        moveVec.setEnabled(false);

        rotate.setMaximumSize(rectangle.getPreferredSize());
        rotate.setMinimumSize(rectangle.getPreferredSize());
        rotate.setEnabled(false);

        scale.setMaximumSize(rectangle.getPreferredSize());
        scale.setMinimumSize(rectangle.getPreferredSize());
        scale.setEnabled(false);

        Box box = Box.createVerticalBox();

        box.add(shapes);
        box.add(line);
        box.add(rectangle);
        box.add(circle);
        box.add(polygon);
        box.add(Box.createVerticalStrut(10));
        box.add(tools);
        box.add(move);
        box.add(resize);
        box.add(select);
        box.add(color);
        box.add(rgbCube);
        box.add(moveVec);
        box.add(rotate);
        box.add(scale);

        add(box);
        clearSelection();
    }

    public void boldShapesButton(ShapeType shapeType) {
        clearSelection();

        switch (shapeType) {
            case LINE:
                line.setBackground(new Color(208, 208, 208));
                break;
            case RECTANGLE:
                rectangle.setBackground(new Color(208, 208, 208));
                break;
            case CIRCLE:
                circle.setBackground(new Color(208, 208, 208));
                break;
            case POLYGON:
                polygon.setBackground(new Color(208, 208, 208));
                break;
        }
    }

    public void boldToolsButton(ToolType toolType) {
        clearSelection();

        switch (toolType) {
            case MOVE:
                move.setBackground(new Color(208, 208, 208));
                break;
            case RESIZE:
                resize.setBackground(new Color(208, 208, 208));
                break;
            case SELECT:
                select.setBackground(new Color(208, 208, 208));
                break;
        }
    }

    private void clearSelection() {
        line.setBackground(new Color(238, 238, 238));
        rectangle.setBackground(new Color(238, 238, 238));
        circle.setBackground(new Color(238, 238, 238));
        polygon.setBackground(new Color(238, 238, 238));
        move.setBackground(new Color(238, 238, 238));
        resize.setBackground(new Color(238, 238, 238));
        select.setBackground(new Color(238, 238, 238));
        color.setBackground(new Color(238, 238, 238));
        rgbCube.setBackground(new Color(238, 238, 238));
        moveVec.setBackground(new Color(238, 238, 238));
        rotate.setBackground(new Color(238, 238, 238));
        scale.setBackground(new Color(238, 238, 238));
    }
}
