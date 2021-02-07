package org.misern.handler.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;
import org.misern.MainFrame;
import org.misern.io.FileType;
import org.misern.io.JPEGLoader;
import org.misern.io.PortablePixelMapLoader;
import org.misern.io.error.ImageLoadException;
import org.misern.io.interfaces.LoadImageStrategy;
import org.misern.ui.PaintPanel;

/**
 * Handler for open file operations
 * @version 0.2
 * @since 0.2
 * @see ActionListener
 */
public class OpenFileActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public OpenFileActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Creates file choose dialog and loads selected image to the paint panel
     * @since 0.2
     * @see ActionListener
     * @param e invoking action event object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.ppm, *.jpg, *.jpeg, *.png", "png", "jpeg", "ppm", "jpg", "png"));

        int returnValue = fileChooser.showOpenDialog(paintPanel);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String type = FilenameUtils.getExtension(selectedFile.getName());
            LoadImageStrategy loadImageStrategy = recognizeFileType(type);

            try {
                BufferedImage loadedImage = loadImageStrategy.loadAsBufferedImage(selectedFile);
                paintPanel.setImage(loadedImage);
                paintPanel.repaint();
            } catch (ImageLoadException ex) {
                JOptionPane.showMessageDialog(paintPanel,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private LoadImageStrategy recognizeFileType(String type) throws IllegalArgumentException {
        if (FileType.JPEG.name().equalsIgnoreCase(type) ||
                FileType.JPG.name().equalsIgnoreCase(type) ||
                FileType.PNG.name().equalsIgnoreCase(type)) {
            return new JPEGLoader();
        } else if (FileType.PPM.name().equalsIgnoreCase(type)) {
            return new PortablePixelMapLoader();
        }

        JOptionPane.showMessageDialog(paintPanel,
                "Unsupported file format.",
                "Error",
                JOptionPane.ERROR_MESSAGE);

        throw new IllegalArgumentException("Unsupported file format.");
    }
}
