package org.misern.handler.histogram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.histogram.Histogram;
import org.misern.ui.HistogramFrame;

public class RestoreOriginalImageActionHandler implements ActionListener {

    private HistogramFrame histogramFrame;

    public RestoreOriginalImageActionHandler(HistogramFrame histogramFrame) {
        this.histogramFrame = histogramFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage originalImage = histogramFrame.getOriginalImage();
        histogramFrame.getImagePanel().setImage(originalImage);
        histogramFrame.getImagePanel().repaint();
        histogramFrame.setHistogramModel(new Histogram(originalImage));

        histogramFrame.createHistogramPanel();
        if (histogramFrame.getActualChannel().equals(Histogram.CHANNELS.GRAYSCALE)) {
            histogramFrame.getImagePanel().setImageInGrayscale();
        } else {
            histogramFrame.getImagePanel().setImageInRGBMode();
        }
    }
}
