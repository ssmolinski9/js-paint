package org.misern.io.error;

/**
 * Error with loading image files into paint panel
 * @version 0.2
 * @since 0.2
 */
public abstract class ImageLoadException extends Exception {
    public ImageLoadException(String message) {
        super(message);
    }
}
