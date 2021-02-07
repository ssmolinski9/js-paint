package org.misern.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import org.misern.utils.MathUtils;

public class AnalyzerFrame extends JFrame {

    private final ImagePanel imagePanel = new ImagePanel();

    private final BufferedImage originalImage;
    private BufferedImage grayscaleImage;

    private final JMenuItem restore = new JMenuItem("Restore image");
    private final JPanel colorPanel = new JPanel();
    private final JLabel hoveredColor = new JLabel("R: -, G: -, B: -");

    public AnalyzerFrame(BufferedImage image) {
        this.setSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout());
        this.setTitle("JS PAINT – Analyzer");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.imagePanel.setImage(image);
        this.originalImage = image;

        createMenuBar();

        colorPanel.setBackground(Color.LIGHT_GRAY);
        colorPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        colorPanel.add(hoveredColor);

        add(imagePanel, BorderLayout.CENTER);
        add(colorPanel, BorderLayout.SOUTH);

        setActionHandlers();
        this.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu options = new JMenu("Options");
        menuBar.add(options);
        options.add(restore);

        add(menuBar, BorderLayout.NORTH);
    }

    private void setActionHandlers() {
        restore.addActionListener(l -> {
            getImagePanel().setImage(originalImage);
            getImagePanel().repaint();
        });

        imagePanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point selectedPoint = MathUtils.getPointOnImage(imagePanel.getOriginalImage(), imagePanel, e.getPoint());

                try {
                    Color selectedColor = new Color(imagePanel.getOriginalImage().getRGB(selectedPoint.x, selectedPoint.y));
                    hoveredColor.setText(String.format("R: %d, G: %d, B: %d", selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue()));
                    colorPanel.setBackground(selectedColor);
                } catch (ArrayIndexOutOfBoundsException ignored) {}
            }
        });

        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point selectedPoint = MathUtils.getPointOnImage(imagePanel.getOriginalImage(), imagePanel, e.getPoint());
                try {
                    Color selectedColor = new Color(imagePanel.getOriginalImage().getRGB(selectedPoint.x, selectedPoint.y));

                    String value = JOptionPane.showInputDialog(getImagePanel(), "Tolerance [0-255]");
                    int tolerance = Integer.parseInt(value);

                    double counter = 0.0;
                    BufferedImage image = imagePanel.getOriginalImage();
                    BufferedImage converted = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

                    for (int y = 0; y < image.getHeight(); y++) {
                        for (int x = 0; x < image.getWidth(); x++) {
                            Color pixel = new Color(image.getRGB(x, y));
                            if (pixel.getRed() <= selectedColor.getRed() + tolerance &&
                                pixel.getRed() >= selectedColor.getRed() - tolerance &&
                                pixel.getGreen() <= selectedColor.getGreen() + tolerance &&
                                pixel.getGreen() >= selectedColor.getGreen() - tolerance &&
                                pixel.getBlue() <= selectedColor.getBlue() + tolerance &&
                                pixel.getBlue() >= selectedColor.getBlue() - tolerance) {
                                counter++;
                                converted.setRGB(x, y, Color.RED.getRGB());
                            } else {
                                converted.setRGB(x, y, pixel.getRGB());
                            }
                        }
                    }

                    getImagePanel().setImage(converted);
                    getImagePanel().repaint();
                    JOptionPane.showMessageDialog(getImagePanel(), String.format("Result: %.2f", counter / (image.getWidth() * image.getHeight()) * 100) + "%");
                } catch (ArrayIndexOutOfBoundsException ignored) {
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(getImagePanel(), "Enter correct tolerance.");
                }
            }
        });
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
