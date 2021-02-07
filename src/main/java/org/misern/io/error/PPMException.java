package org.misern.io.error;

/**
 * Error with loading PPM file
 * @version 0.2
 * @since 0.2
 * @see ImageLoadException
 * @see org.misern.io.PortablePixelMapLoader
 */
public class PPMException extends ImageLoadException {
    public PPMException(String message) {
        super(message);
    }
}
