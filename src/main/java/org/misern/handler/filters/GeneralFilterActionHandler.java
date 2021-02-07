package org.misern.handler.filters;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.misern.MainFrame;
import org.misern.filter.GeneralFilter;
import org.misern.ui.PaintPanel;

/**
 * Action handler for adding pixels to image
 * @version 0.4
 * @since 0.4
 * @see ActionListener
 */
public class GeneralFilterActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public GeneralFilterActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Handles occurred action for adding pixels
     * @param e action event created by clicking on the filter button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = paintPanel.getImage();
        String value = JOptionPane.showInputDialog(this.paintPanel, "Mask size");
        JSpinner [][] spinners;

        try {
            int converted = Integer.parseInt(value);
            if (converted % 2 == 0) {
                JOptionPane.showMessageDialog(paintPanel, "Wrong value! Mask should has odd size", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog();
            dialog.setSize(400, 400);
            dialog.setLayout(new BorderLayout());
            dialog.setTitle("Fill mask");

            JButton filter = new JButton("Filter");
            JPanel panel = new JPanel(new GridLayout(converted, converted));

            spinners = new JSpinner[converted][converted];
            for (int i = 0; i < converted; i++) {
                for (int j = 0; j < converted; j++) {
                    spinners[i][j] = new JSpinner(new SpinnerNumberModel(1.0, 0.0, Double.MAX_VALUE, 0.5));
                    panel.add(spinners[i][j]);
                }
            }

            filter.addActionListener(event -> {
                BufferedImage filteredImage;

                double [][] mask = new double[spinners.length][spinners.length];
                for (int i = 0; i < mask.length; i++) {
                    for (int j = 0; j < mask.length; j++) {
                        mask[i][j] = (double) spinners[i][j].getValue();
                    }
                }

                GeneralFilter generalFilter = new GeneralFilter();
                filteredImage = generalFilter.filter(image, mask, mask.length);
                paintPanel.setImage(filteredImage);
                paintPanel.repaint();
                dialog.dispose();
            });

            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(filter, BorderLayout.SOUTH);
            dialog.setVisible(true);
        } catch(NumberFormatException ignored) {}
    }
}
