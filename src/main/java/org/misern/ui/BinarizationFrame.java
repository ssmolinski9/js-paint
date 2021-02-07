package org.misern.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.misern.handler.binary.BlackPercentageBinarizationActionHandler;
import org.misern.handler.binary.BlackPercentageBinarizationComponentsActionHandler;
import org.misern.handler.binary.GrayscaleConvertHandler;
import org.misern.handler.binary.ManualBinarizationActionHandler;
import org.misern.handler.binary.ManualBinarizationComponentsActionHandler;
import org.misern.handler.binary.OtsuBinarizationActionHandler;

public class BinarizationFrame extends JFrame {

    private final ImagePanel imagePanel = new ImagePanel();

    private final BufferedImage originalImage;
    private BufferedImage grayscaleImage;

    private final JMenuItem red = new JMenuItem("Red channel");
    private final JMenuItem green = new JMenuItem("Green channel");
    private final JMenuItem blue = new JMenuItem("Blue channel");
    private final JMenuItem average = new JMenuItem("Average channel");
    private final JMenuItem manually = new JMenuItem("Manually");
    private final JMenuItem percentage = new JMenuItem("Black percentage");
    private final JMenuItem otsu = new JMenuItem("Otsu's method");

    private final JTextField binarizationLevel = new JTextField("0");
    private final JTextField percentageLevel = new JTextField("0");

    private final JButton confirmManuallyBinarization = new JButton("Manual");
    private final JButton confirmPercentageBinarization = new JButton("Percentage");

    public BinarizationFrame(BufferedImage image) {
        this.setSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout());
        this.setTitle("JS PAINT – Binarization");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.imagePanel.setImage(image);
        this.originalImage = image;

        createMenuBar();

        JPanel componentsPanel = new JPanel();
        componentsPanel.add(binarizationLevel);
        componentsPanel.add(percentageLevel);
        componentsPanel.add(confirmManuallyBinarization);
        componentsPanel.add(confirmPercentageBinarization);

        add(imagePanel, BorderLayout.CENTER);
        add(componentsPanel, BorderLayout.SOUTH);

        setActionHandlers();

        red.doClick();

        this.binarizationLevel.setEnabled(false);
        this.percentageLevel.setEnabled(false);
        this.confirmPercentageBinarization.setEnabled(false);
        this.confirmManuallyBinarization.setEnabled(false);
        this.binarizationLevel.setPreferredSize(new Dimension(150, 20));
        this.percentageLevel.setPreferredSize(new Dimension(150, 20));

        this.binarizationLevel.setToolTipText("Parameter");
        this.percentageLevel.setToolTipText("Parameter");

        this.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu grayscale = new JMenu("Grayscale");

        JMenu options = new JMenu("Options");

        menuBar.add(grayscale);
        grayscale.add(red);
        grayscale.add(green);
        grayscale.add(blue);
        grayscale.add(average);

        menuBar.add(options);
        options.add(manually);
        options.add(percentage);
        options.add(otsu);

        add(menuBar, BorderLayout.NORTH);
    }

    private void setActionHandlers() {
        red.addActionListener(new GrayscaleConvertHandler(this, Color.RED));
        green.addActionListener(new GrayscaleConvertHandler(this, Color.GREEN));
        blue.addActionListener(new GrayscaleConvertHandler(this, Color.BLUE));
        average.addActionListener(new GrayscaleConvertHandler(this, Color.GRAY));
        manually.addActionListener(new ManualBinarizationComponentsActionHandler(this));
        percentage.addActionListener(new BlackPercentageBinarizationComponentsActionHandler(this));
        confirmManuallyBinarization.addActionListener(new ManualBinarizationActionHandler(this));
        otsu.addActionListener(new OtsuBinarizationActionHandler(this));
        confirmPercentageBinarization.addActionListener(new BlackPercentageBinarizationActionHandler(this));
    }

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public JTextField getBinarizationLevel() {
        return binarizationLevel;
    }

    public JTextField getPercentageLevel() {
        return percentageLevel;
    }

    public JButton getConfirmManuallyBinarization() {
        return confirmManuallyBinarization;
    }

    public JButton getConfirmPercentageBinarization() {
        return confirmPercentageBinarization;
    }

    public BufferedImage getGrayscaleImage() {
        return grayscaleImage;
    }

    public void setGrayscaleImage(BufferedImage transformedImage) {
        this.grayscaleImage = transformedImage;
    }
}
