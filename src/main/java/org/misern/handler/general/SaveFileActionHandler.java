package org.misern.handler.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.misern.MainFrame;
import org.misern.io.JPEGSaver;
import org.misern.ui.PaintPanel;

/**
 * Handler for saving file operations
 * @version 0.2
 * @since 0.2
 * @see ActionListener
 */
public class SaveFileActionHandler implements ActionListener {

    private final PaintPanel paintPanel;

    /**
     * Creates action handler for provided frame
     * @param frame frame with paint and input panel elements
     */
    public SaveFileActionHandler(MainFrame frame) {
        this.paintPanel = frame.getPaintPanel();
    }

    /**
     * Creates file choose dialog and save selected image to the JPG file
     * @since 0.2
     * @see ActionListener
     * @param e invoking action event object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.jpg", "jpg"));

        int returnValue = fileChooser.showSaveDialog(paintPanel);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JPEGSaver saver = new JPEGSaver();

            JFormattedTextField compressionLevel = new JFormattedTextField(new DecimalFormat("#.##"));
            JOptionPane.showMessageDialog(null, compressionLevel, "Enter compression level", JOptionPane.QUESTION_MESSAGE);

            try {
                saver.save(paintPanel, selectedFile, Float.parseFloat(String.valueOf(compressionLevel.getValue())));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(paintPanel,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
