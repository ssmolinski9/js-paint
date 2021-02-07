package org.misern.handler.histogram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.ui.HistogramFrame;
import org.misern.ui.PaintPanel;

public class ShowHistogramFrameActionHandler implements ActionListener {

    private PaintPanel paintPanel;

    public ShowHistogramFrameActionHandler(PaintPanel paintPanel) {
        this.paintPanel = paintPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = paintPanel.getImage();
        if (image != null) {
            new HistogramFrame(image);
        }
    }
}
