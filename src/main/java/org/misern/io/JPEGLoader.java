package org.misern.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.misern.io.error.JPEGException;
import org.misern.io.interfaces.LoadImageStrategy;

/**
 * Loader for the JPEG format
 * @version 0.2
 * @since 0.2
 */
public class JPEGLoader implements LoadImageStrategy {
    /**
     * @see LoadImageStrategy
     * @param file selected JPEG file
     * @throws JPEGException occurs when there are problems with selected file
     */
    @Override
    public BufferedImage loadAsBufferedImage(File file) throws JPEGException {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new JPEGException(e.getMessage());
        }
    }
}
