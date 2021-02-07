package org.misern.io;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import org.misern.ui.PaintPanel;

/**
 * Saver for the JPEG format
 * @version 0.2
 * @since 0.2
 */
public class JPEGSaver {
    /**
     * Saves content of the paint panel into the file
     * @param panel paint panel which content should be saved
     * @param file file to save content
     * @param quality quality of the jpg compression
     */
    public void save(PaintPanel panel, File file, float quality) throws IOException {
        BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.print(img.getGraphics());

        JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
        jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(quality);

        final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        writer.setOutput(new FileImageOutputStream(file));
        writer.write(null, new IIOImage(img, null, null), jpegParams);
    }
}
