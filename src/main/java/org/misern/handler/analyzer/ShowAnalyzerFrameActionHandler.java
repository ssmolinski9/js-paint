package org.misern.handler.analyzer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.ui.AnalyzerFrame;
import org.misern.ui.PaintPanel;

public class ShowAnalyzerFrameActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    public ShowAnalyzerFrameActionHandler(PaintPanel paintPanel) {
        this.paintPanel = paintPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = paintPanel.getImage();
        if (image != null) {
            new AnalyzerFrame(image);
        }
    }
}
