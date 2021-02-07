package org.misern.handler.histogram;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.misern.histogram.Histogram;
import org.misern.ui.HistogramFrame;

public class ChangeChannelActionHandler implements ActionListener {

    private HistogramFrame histogramFrame;
    private Color color;

    public ChangeChannelActionHandler(HistogramFrame histogramFrame, Color color) {
        this.histogramFrame = histogramFrame;
        this.color = color;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String channel;
        if (color == Color.RED) {
            channel = Histogram.CHANNELS.RED;
        } else if (color == Color.GREEN) {
            channel = Histogram.CHANNELS.GREEN;
        } else if (color == Color.BLUE) {
            channel = Histogram.CHANNELS.BLUE;
        } else if (color == Color.MAGENTA) {
            channel = Histogram.CHANNELS.AVERAGE;
        } else {
            channel = Histogram.CHANNELS.GRAYSCALE;
        }

        histogramFrame.setActualChannel(channel);
        histogramFrame.setActualColor(color);
        histogramFrame.createHistogramPanel();
        histogramFrame.repaint();

        if (channel.equals(Histogram.CHANNELS.GRAYSCALE)) {
            histogramFrame.getImagePanel().setImageInGrayscale();
        } else {
            histogramFrame.getImagePanel().setImageInRGBMode();
        }
    }
}
