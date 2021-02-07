package org.misern.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.misern.handler.morphology.ClosureActionHandler;
import org.misern.handler.morphology.DilatationActionHandler;
import org.misern.handler.morphology.ErosionActionHandler;
import org.misern.handler.morphology.OpeningActionHandler;
import org.misern.utils.MathUtils;

public class MorphologyFrame extends JFrame {

    private final ImagePanel imagePanel = new ImagePanel();

    private final BufferedImage originalImage;
    private BufferedImage grayscaleImage;

    private final JMenuItem restore = new JMenuItem("Restore image");
    private final JMenuItem dilatation = new JMenuItem("Dilatation");
    private final JMenuItem erosion = new JMenuItem("Erosion");
    private final JMenuItem opening = new JMenuItem("Opening");
    private final JMenuItem closure = new JMenuItem("Closure");
    private final JMenuItem thinning = new JMenuItem("Thinning");
    private final JMenuItem thickening = new JMenuItem("Thickening");

    public MorphologyFrame(BufferedImage image) {
        this.setSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout());
        this.setTitle("JS PAINT – Morphology");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.imagePanel.setImage(image);
        this.originalImage = image;

        createMenuBar();

        add(imagePanel, BorderLayout.CENTER);
        setActionHandlers();

        convertToGrayscale();
        binarizeImage();
        this.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu options = new JMenu("Options");
        menuBar.add(options);
        options.add(restore);
        options.add(dilatation);
        options.add(erosion);
        options.add(opening);
        options.add(closure);
        options.add(thinning);
        options.add(thickening);

        add(menuBar, BorderLayout.NORTH);
    }

    private void setActionHandlers() {
        restore.addActionListener(l -> {
            getImagePanel().setImage(originalImage);
            this.convertToGrayscale();
            this.binarizeImage();
        });

        dilatation.addActionListener(new DilatationActionHandler(this));
        erosion.addActionListener(new ErosionActionHandler(this));
        opening.addActionListener(new OpeningActionHandler(this));
        closure.addActionListener(new ClosureActionHandler(this));
    }

    private void convertToGrayscale() {
        BufferedImage image = getOriginalImage();
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < transformedImage.getWidth(); x++) {
            for (int y = 0; y < transformedImage.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));

                int colorValue = (pixelColor.getRed() + pixelColor.getBlue() + pixelColor.getGreen()) / 3;
                transformedImage.setRGB(x, y, new Color(colorValue, colorValue, colorValue).getRGB());
            }
        }

        setGrayscaleImage(transformedImage);
        getImagePanel().setImage(transformedImage);
        getImagePanel().repaint();
    }

    private void binarizeImage() {
        BufferedImage image = getGrayscaleImage();
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Integer level = MathUtils.getOtsuThreshold(image);

        for (int x = 0; x < transformedImage.getWidth(); x++) {
            for (int y = 0; y < transformedImage.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                int colorValue = pixelColor.getRed();
                transformedImage.setRGB(x, y, colorValue > level ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        getImagePanel().setImage(transformedImage);
        grayscaleImage = transformedImage;
        getImagePanel().repaint();
    }

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public BufferedImage getGrayscaleImage() {
        return grayscaleImage;
    }

    public void setGrayscaleImage(BufferedImage transformedImage) {
        this.grayscaleImage = transformedImage;
    }
}
