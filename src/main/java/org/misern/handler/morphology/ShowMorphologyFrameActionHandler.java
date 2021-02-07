package org.misern.handler.morphology;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.ui.MorphologyFrame;
import org.misern.ui.PaintPanel;

public class ShowMorphologyFrameActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    public ShowMorphologyFrameActionHandler(PaintPanel paintPanel) {
        this.paintPanel = paintPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = paintPanel.getImage();
        if (image != null) {
            new MorphologyFrame(image);
        }
    }
}
