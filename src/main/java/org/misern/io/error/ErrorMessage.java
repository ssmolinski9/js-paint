package org.misern.io.error;

/**
 * Input/Output operations error messages
 * @version 0.2
 * @since 0.2
 */
public enum ErrorMessage {
    PPM_WRONG_VERSION("Unsupported PPM version"),
    PPM_MALFORMED_FILE("Malformed PPM file"),
    PPM_UNEXPECTED_EOF("Unexpected end of file"),
    PPM_TOO_LARGE_VALUE("One of the color value is out of the range");

    private final String description;

    ErrorMessage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
