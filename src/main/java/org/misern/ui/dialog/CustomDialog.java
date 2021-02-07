package org.misern.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import lombok.Getter;
import org.misern.shapes.Polygon;
import org.misern.shapes.Shape;

/**
 * Custom dialog for edition points coords
 * @version 0.1
 * @since 0.1
 * @see JDialog
 * @apiNote Custom dialog for simple editing points can be created by DialogFactory
 */
public class CustomDialog extends JDialog {
    @Getter
    private final JButton accept = new JButton("Accept");
    private final JButton cancel = new JButton("Cancel");

    private final JTextField startPointXInput = new JTextField("0");
    private final JTextField startPointYInput = new JTextField("0");

    private final JTextField endPointXInput = new JTextField("0");
    private final JTextField endPointYInput = new JTextField("0");

    private final JTextField angleInput = new JTextField("0");

    private final JTextField scaleXInput = new JTextField("0");
    private final JTextField scaleYInput = new JTextField("0");


    /**
     * Creates and shows new dialog
     * @param frame parent frame for the dialog
     */
    public CustomDialog(JFrame frame, boolean standardFields) {
        super(frame);

        if (standardFields) {
            createDialog(frame);
            createHandlers();
        }
    }

    /**
     * Creates and shows new dialog filled with existing shape coords
     * @param frame parent frame for the dialog
     * @param shape existing shape for gets its coords
     */
    public CustomDialog(JFrame frame, Shape shape) {
        super(frame);

        if (shape instanceof Polygon) {
            createSimpleDialog(frame);
        } else {
            createDialog(frame);
            startPointXInput.setText(shape.getStart().x + "");
            startPointYInput.setText(shape.getStart().y + "");

            endPointXInput.setText(shape.getEnd().x + "");
            endPointYInput.setText(shape.getEnd().y + "");
        }

        createHandlers();
    }

    /**
     * Creates and shows new dialog filled with existing polygon coords
     * @param frame parent frame for the dialog
     * @param polygon existing polygon for edition
     * @param selected polygon point for coords
     */
    public CustomDialog(JFrame frame, Polygon polygon, Point selected) {
        super(frame);
        createSimpleDialog(frame);
        createHandlers();

        startPointXInput.setText(polygon.getNearest(selected).x + "");
        startPointYInput.setText(polygon.getNearest(selected).y + "");
    }

    public CustomDialog(JFrame frame, Polygon polygon) {
        super(frame);
        createSimpleDialog(frame);
        createHandlers();

        startPointXInput.setText("0");
        startPointYInput.setText("0");
    }

    private void createDialog(JFrame frame) {
        setSize(new Dimension(400, 200));
        setResizable(false);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        JLabel startPointX = new JLabel("X:");
        JLabel startPointY = new JLabel("Y:");

        JLabel endPointX = new JLabel("X:");
        JLabel endPointY = new JLabel("Y:");

        Box mainBox = Box.createVerticalBox();

        Box firstRow = Box.createHorizontalBox();
        firstRow.add(Box.createHorizontalStrut(15));
        firstRow.add(startPointX);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointXInput);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointY);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointYInput);
        firstRow.add(Box.createHorizontalStrut(15));

        Box secondRow = Box.createHorizontalBox();
        secondRow.add(Box.createHorizontalStrut(15));
        secondRow.add(endPointX);
        secondRow.add(Box.createHorizontalStrut(10));
        secondRow.add(endPointXInput);
        secondRow.add(Box.createHorizontalStrut(10));
        secondRow.add(endPointY);
        secondRow.add(Box.createHorizontalStrut(10));
        secondRow.add(endPointYInput);
        secondRow.add(Box.createHorizontalStrut(15));

        mainBox.add(Box.createVerticalStrut(25));
        mainBox.add(Box.createGlue());
        mainBox.add(firstRow);
        mainBox.add(Box.createVerticalStrut(5));
        mainBox.add(secondRow);
        mainBox.add(Box.createVerticalStrut(25));

        add(mainBox, BorderLayout.CENTER);
        createMenuBar();
    }

    public void createSimpleDialog(JFrame frame) {
        setSize(new Dimension(400, 200));
        setResizable(false);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        JLabel startPointX = new JLabel("X:");
        JLabel startPointY = new JLabel("Y:");

        Box mainBox = Box.createVerticalBox();

        Box firstRow = Box.createHorizontalBox();
        firstRow.add(Box.createHorizontalStrut(15));
        firstRow.add(startPointX);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointXInput);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointY);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointYInput);
        firstRow.add(Box.createHorizontalStrut(15));

        mainBox.add(Box.createVerticalStrut(25));
        mainBox.add(Box.createGlue());
        mainBox.add(firstRow);
        mainBox.add(Box.createVerticalStrut(25));

        add(mainBox, BorderLayout.CENTER);
        createMenuBar();
    }

    public void createRotateDialog(JFrame frame) {
        setSize(new Dimension(400, 200));
        setResizable(false);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        JLabel startPointX = new JLabel("X:");
        JLabel startPointY = new JLabel("Y:");
        JLabel angle = new JLabel("Angle:");

        Box mainBox = Box.createVerticalBox();

        Box firstRow = Box.createHorizontalBox();
        firstRow.add(Box.createHorizontalStrut(15));
        firstRow.add(startPointX);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointXInput);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointY);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointYInput);
        firstRow.add(Box.createHorizontalStrut(15));

        Box secondBox = Box.createHorizontalBox();
        secondBox.add(Box.createHorizontalStrut(15));
        secondBox.add(angle);
        secondBox.add(Box.createHorizontalStrut(10));
        secondBox.add(angleInput);
        secondBox.add(Box.createHorizontalStrut(15));

        mainBox.add(Box.createVerticalStrut(25));
        mainBox.add(Box.createGlue());
        mainBox.add(firstRow);
        mainBox.add(Box.createVerticalStrut(25));
        mainBox.add(secondBox);
        mainBox.add(Box.createVerticalStrut(25));

        add(mainBox, BorderLayout.CENTER);
        createMenuBar();
    }

    public void createScaleDialog(JFrame frame) {
        setSize(new Dimension(400, 200));
        setResizable(false);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        JLabel startPointX = new JLabel("X:");
        JLabel startPointY = new JLabel("Y:");

        JLabel endPointX = new JLabel("Sx:");
        JLabel endPointY = new JLabel("Sy:");

        Box mainBox = Box.createVerticalBox();

        Box firstRow = Box.createHorizontalBox();
        firstRow.add(Box.createHorizontalStrut(15));
        firstRow.add(startPointX);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointXInput);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointY);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(startPointYInput);
        firstRow.add(Box.createHorizontalStrut(15));

        Box secondRow = Box.createHorizontalBox();
        secondRow.add(Box.createHorizontalStrut(15));
        secondRow.add(endPointX);
        secondRow.add(Box.createHorizontalStrut(10));
        secondRow.add(scaleXInput);
        secondRow.add(Box.createHorizontalStrut(10));
        secondRow.add(endPointY);
        secondRow.add(Box.createHorizontalStrut(10));
        secondRow.add(scaleYInput);
        secondRow.add(Box.createHorizontalStrut(15));

        mainBox.add(Box.createVerticalStrut(25));
        mainBox.add(Box.createGlue());
        mainBox.add(firstRow);
        mainBox.add(Box.createVerticalStrut(5));
        mainBox.add(secondRow);
        mainBox.add(Box.createVerticalStrut(25));

        add(mainBox, BorderLayout.CENTER);
        createMenuBar();
    }

    private void createMenuBar() {
        Box mainBox = Box.createVerticalBox();

        Box box = Box.createHorizontalBox();
        box.add(Box.createGlue());
        box.add(cancel);
        box.add(Box.createHorizontalStrut(10));
        box.add(accept);
        box.add(Box.createGlue());

        mainBox.add(box);
        mainBox.add(Box.createVerticalStrut(5));

        add(mainBox, BorderLayout.PAGE_END);
    }

    private void createHandlers() {
        cancel.addActionListener(e -> this.dispose());
    }

    /**
     * @see JDialog
     * @param title new title of the dialog
     */
    public void setTitle(String title) {
        super.setTitle(title);
    }

    /**
     * Gets integer form the start point x value
     * @return integer from input field
     */
    public Integer getClearStartPointXValue() {
        return Integer.parseInt(startPointXInput.getText());
    }

    /**
     * Gets integer form the start point y value
     * @return integer from input field
     */
    public Integer getClearStartPointYValue() {
        return Integer.parseInt(startPointYInput.getText());
    }

    public Double getClearAngleValue() {
        return Double.parseDouble(angleInput.getText());
    }

    public Double getClearScaleXValue() {
        return Double.parseDouble(scaleXInput.getText());
    }

    public Double getClearScaleYValue() {
        return Double.parseDouble(scaleYInput.getText());
    }

    /**
     * Gets integer form the end point x value
     * @return integer from input field
     */
    public Integer getClearEndPointXValue() {
        return Integer.parseInt(endPointXInput.getText());
    }

    /**
     * Gets integer form the end point y value
     * @return integer from input field
     */
    public Integer getClearEndPointYValue() {
        return Integer.parseInt(endPointYInput.getText());
    }
}
