package org.misern.handler.histogram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.misern.histogram.Histogram;
import org.misern.ui.HistogramFrame;
import org.misern.utils.HistogramUtils;

public class DarkenImageActionHandler implements ActionListener {

    private HistogramFrame histogramFrame;

    public DarkenImageActionHandler(HistogramFrame histogramFrame) {
        this.histogramFrame = histogramFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage originalImage = histogramFrame.getOriginalImage();
        BufferedImage transformedImage = HistogramUtils.darken(originalImage);

        histogramFrame.getImagePanel().setImage(transformedImage);
        histogramFrame.getImagePanel().repaint();
        histogramFrame.setHistogramModel(new Histogram(transformedImage));

        double [] redChannel = histogramFrame.getHistogramModel().getHistogram().get(Histogram.CHANNELS.RED);
        histogramFrame.createHistogramPanel();
    }
}
