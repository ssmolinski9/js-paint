package org.misern.handler.binary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.ui.BinarizationFrame;
import org.misern.ui.PaintPanel;

public class ShowBinarizationFrameActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    public ShowBinarizationFrameActionHandler(PaintPanel paintPanel) {
        this.paintPanel = paintPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = paintPanel.getImage();
        if (image != null) {
            new BinarizationFrame(image);
        }
    }
}
