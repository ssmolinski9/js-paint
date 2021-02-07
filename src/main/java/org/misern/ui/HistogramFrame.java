package org.misern.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.misern.handler.histogram.BrightenImageActionHandler;
import org.misern.handler.histogram.ChangeChannelActionHandler;
import org.misern.handler.histogram.DarkenImageActionHandler;
import org.misern.handler.histogram.EqualizeHistogramActionHandler;
import org.misern.handler.histogram.RestoreOriginalImageActionHandler;
import org.misern.handler.histogram.StretchingHistogramActionHandler;
import org.misern.histogram.Histogram;

public class HistogramFrame extends JFrame {

    private Histogram histogramModel;

    private ImagePanel imagePanel = new ImagePanel();
    private ChartPanel chartPanel = new ChartPanel(null);

    private JMenuItem red = new JMenuItem("Red channel");
    private JMenuItem green = new JMenuItem("Green channel");
    private JMenuItem blue = new JMenuItem("Blue channel");
    private JMenuItem avg = new JMenuItem("Average histogram");
    private JMenuItem grayscale = new JMenuItem("Gray scale");
    private JMenuItem equalization = new JMenuItem("Equalize histogram");
    private JMenuItem stretching = new JMenuItem("Stretching histogram");
    private JMenuItem brighten = new JMenuItem("Brighten image");
    private JMenuItem darken = new JMenuItem("Darken image");
    private JMenuItem restore = new JMenuItem("Restore original image");

    private BufferedImage originalImage;

    private Color actualColor;
    private String actualChannel;

    public HistogramFrame(BufferedImage image) {
        this.setSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout());
        this.setTitle("JS PAINT – Histogram");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.histogramModel = new Histogram(image);
        this.imagePanel.setImage(image);
        this.originalImage = image;

        createMenuBar();

        this.actualColor = Color.RED;
        this.actualChannel = Histogram.CHANNELS.RED;
        createHistogramPanel();

        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.5);

        splitPane.setLeftComponent(imagePanel);
        splitPane.setRightComponent(chartPanel);
        add(splitPane, BorderLayout.CENTER);

        addChangeChannelEvent();
        this.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu histograms = new JMenu("Histogram");
        histograms.add(red);
        histograms.add(green);
        histograms.add(blue);
        histograms.add(avg);
        histograms.add(grayscale);

        JMenu operations = new JMenu("Operations");
        operations.add(equalization);
        operations.add(stretching);
        operations.add(brighten);
        operations.add(darken);
        operations.add(restore);

        menuBar.add(histograms);
        menuBar.add(operations);

        add(menuBar, BorderLayout.NORTH);
    }

    public void createHistogramPanel() {
        final double [] channel = histogramModel.getHistogram().get(actualChannel);
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < channel.length; i++) {
            dataset.addValue(channel[i], "value", i+"");
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Histogram",
                "",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                false, false, false);

        barChart.getCategoryPlot().getDomainAxis().setVisible(false);
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, actualColor);
        this.chartPanel.setChart(barChart);
        this.chartPanel.repaint();
    }

    private void addChangeChannelEvent() {
        red.addActionListener(new ChangeChannelActionHandler(this, Color.RED));
        green.addActionListener(new ChangeChannelActionHandler(this, Color.GREEN));
        blue.addActionListener(new ChangeChannelActionHandler(this, Color.BLUE));
        avg.addActionListener(new ChangeChannelActionHandler(this, Color.MAGENTA));
        grayscale.addActionListener(new ChangeChannelActionHandler(this, Color.BLACK));
        equalization.addActionListener(new EqualizeHistogramActionHandler(this));
        stretching.addActionListener(new StretchingHistogramActionHandler(this));
        brighten.addActionListener(new BrightenImageActionHandler(this));
        darken.addActionListener(new DarkenImageActionHandler(this));
        restore.addActionListener(new RestoreOriginalImageActionHandler(this));
    }

    public Histogram getHistogramModel() {
        return histogramModel;
    }

    public void setHistogramModel(Histogram histogramModel) {
        this.histogramModel = histogramModel;
    }

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public Color getActualColor() {
        return actualColor;
    }

    public void setActualColor(Color actualColor) {
        this.actualColor = actualColor;
    }

    public String getActualChannel() {
        return actualChannel;
    }

    public void setActualChannel(String actualChannel) {
        this.actualChannel = actualChannel;
    }
}
