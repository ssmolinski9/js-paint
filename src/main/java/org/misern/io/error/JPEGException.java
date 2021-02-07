package org.misern.io.error;

/**
 * Error with loading JPEG file
 * @version 0.2
 * @since 0.2
 * @see ImageLoadException
 * @see org.misern.io.JPEGLoader
 */
public class JPEGException extends ImageLoadException {
    public JPEGException(String message) {
        super(message);
    }
}
