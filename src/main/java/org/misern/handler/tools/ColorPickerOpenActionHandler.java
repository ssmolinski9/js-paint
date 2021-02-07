package org.misern.handler.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import org.misern.MainFrame;
import org.misern.ui.PaintPanel;
import org.misern.ui.colors.CMYKChooser;
import org.misern.ui.colors.RGBChooser;

/**
 * Action handler for opening colors tool
 * @version 0.3
 * @since 0.3
 * @see ActionListener
 */
public class ColorPickerOpenActionHandler implements ActionListener {

    private final MainFrame frame;

    private final RGBChooser rgbChooser;
    private final CMYKChooser cmykChooser;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public ColorPickerOpenActionHandler(MainFrame frame) {
        this.frame = frame;

        PaintPanel paintPanel = frame.getPaintPanel();
        this.rgbChooser = new RGBChooser(paintPanel);
        this.cmykChooser = new CMYKChooser(paintPanel);
    }

    /**
     * Handles occurred action by clicking on color chooser
     * @param e action event created by clicking color chooser button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog dialog = new JDialog(frame);
        dialog.setSize(500, 350);
        dialog.setTitle("Choose color");

        JTabbedPane cards = new JTabbedPane();
        cards.add(rgbChooser, "RGB");
        cards.add(cmykChooser, "CMYK");
        cards.addChangeListener(changeEvent -> {
            JTabbedPane triggeredPane = (JTabbedPane) changeEvent.getSource();

            if (triggeredPane.getSelectedIndex() == 0) {
                rgbChooser.updateRGB(cmykChooser.getCyanSlider().getValue(),
                        cmykChooser.getMagentaSlider().getValue(),
                        cmykChooser.getYellowSlider().getValue(),
                        cmykChooser.getKeySlider().getValue());
            } else {
                cmykChooser.updateKeyValue(rgbChooser.getRedSlider().getValue(),
                        rgbChooser.getGreenSlider().getValue(),
                        rgbChooser.getBlueSlider().getValue());
            }
        });

        dialog.add(cards);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
