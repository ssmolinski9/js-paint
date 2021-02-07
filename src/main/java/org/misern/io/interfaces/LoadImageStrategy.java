package org.misern.io.interfaces;

import java.awt.image.BufferedImage;
import java.io.File;
import org.misern.io.error.ImageLoadException;

/**
 * Strategy interface for different loading image types
 * @version 0.2
 * @since 0.2
 */
public interface LoadImageStrategy {
    /**
     * Loads given file and converts it into image type
     * @param file selected image file
     * @return BufferedImage with exact dimensions and content as selected file
     * @throws ImageLoadException occurs when there are problems with selected file
     * @see BufferedImage
     */
    BufferedImage loadAsBufferedImage(File file) throws ImageLoadException;
}
