package org.misern.io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.misern.io.error.ErrorMessage;
import org.misern.io.error.PPMException;
import org.misern.io.interfaces.LoadImageStrategy;

/**
 * Loader for the PPM format
 * @version 0.2
 * @since 0.2
 */
public class PortablePixelMapLoader implements LoadImageStrategy {

    private static final Logger logger = Logger.getLogger(PortablePixelMapLoader.class.getSimpleName());

    /**
     * @see LoadImageStrategy
     * @param file selected PPM file
     * @throws PPMException occurs when there are problems with PPM file
     */
    @Override
    public BufferedImage loadAsBufferedImage(File file) throws PPMException {
        BufferedReader br = createBufferedReader(file);

        BufferedImage image;

        int width, height, depth;
        int [] colors;

        try {
            String version = getVersion(br);

            final int[] dimension = getImageDimension(br);
            width = dimension[0];
            height = dimension[1];

            depth = getColorDepth(br);
            colors = new int[width*height*3];

            if (version.equalsIgnoreCase("P3")) {
                processPPM3File(br, width, height, depth, colors);
            } else if (version.equalsIgnoreCase("P6")) {
                br.close();
                processPPM6File(file, width, height, depth, colors);
            }

            image = createImage(colors, width, height, depth);
        } catch (NoSuchElementException | IOException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            throw new PPMException(ErrorMessage.PPM_UNEXPECTED_EOF.getDescription());
        } finally {
            try {
                br.close();
            } catch (IOException ignored) {}
        }

        return image;
    }

    private BufferedReader createBufferedReader(File file) throws PPMException {
        try {
            return new BufferedReader(new FileReader(file));
        } catch (IOException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            throw new PPMException(ErrorMessage.PPM_MALFORMED_FILE.getDescription());
        }
    }

    private String getVersion(BufferedReader br) throws PPMException {
        String version;

        try {
            version = br.readLine().trim();
        } catch (NoSuchElementException | IOException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            throw new PPMException(ErrorMessage.PPM_UNEXPECTED_EOF.getDescription());
        }

        if (!version.equalsIgnoreCase("P3") && !version.equalsIgnoreCase("P6")) {
            throw new PPMException(ErrorMessage.PPM_WRONG_VERSION.getDescription());
        }

        logger.log(Level.INFO, "Detected PPM file in " + version + " version");
        return version;
    }

    private int[] getImageDimension(BufferedReader br) throws PPMException {
        try {
            int [] lineNumbers = getIntegersFromLine(br);
            if (lineNumbers == null || lineNumbers.length != 2) {
                throw new PPMException(ErrorMessage.PPM_MALFORMED_FILE.getDescription());
            }

            int width = lineNumbers[0];
            int height = lineNumbers[1];

            logger.log(Level.INFO, String.format("Image based on PPM file will be %d x %d%n", width, height));
            return new int[]{width, height};
        } catch (NoSuchElementException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            throw new PPMException(ErrorMessage.PPM_UNEXPECTED_EOF.getDescription());
        }
    }

    private int getColorDepth(BufferedReader br) throws PPMException {
        try {
            int depth = getIntegerFromSingleDigitLine(br);

            logger.log(Level.INFO, String.format("Image based on PPM file will have %d-bit depth%n", (int)Math.sqrt(depth+1)));
            return depth;
        } catch (NoSuchElementException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            throw new PPMException(ErrorMessage.PPM_UNEXPECTED_EOF.getDescription());
        }
    }

    private void processPPM3File(BufferedReader br, int width, int height, int depth, int[] colors) throws PPMException {
        // Counter of the color values, after reading file it should be equals (width * height) * 3
        int counter = 0;

        int [] lineDigits;
        while ( (lineDigits = getIntegersFromLine(br)) != null ) {
            for (int lineDigit : lineDigits) {
                if (lineDigit > depth) {
                    throw new PPMException(ErrorMessage.PPM_TOO_LARGE_VALUE.getDescription());
                }

                colors[counter] = lineDigit;
                counter++;
            }
        }

        if (counter != width * height * 3) {
            throw new PPMException(ErrorMessage.PPM_UNEXPECTED_EOF.getDescription());
        }
    }

    private void processPPM6File(File file, int width, int height, int depth, int[] colors) throws PPMException {
        // Counter of the color values, after reading file it should be equals (width * height) * 3
        int counter = 0;
        try {
            InputStream inputStream = new FileInputStream(file);
            byte [] content = readWholeFile(file, inputStream);

            int colorDataStartIndex = calculateStartIndex(content);
            for (int i = colorDataStartIndex+1; i <= content.length-1; i++) {
                int colorValue = unsignedToBytes(content[i]);
                if (colorValue > depth) {
                    throw new PPMException(ErrorMessage.PPM_TOO_LARGE_VALUE.getDescription());
                }

                colors[counter] = colorValue;
                counter++;
            }
        } catch (IOException ex) {
            throw new PPMException(ErrorMessage.PPM_MALFORMED_FILE.getDescription());
        }

        if (counter != width * height * 3) {
            throw new PPMException(ErrorMessage.PPM_UNEXPECTED_EOF.getDescription());
        }
    }

    private int calculateStartIndex(byte[] content) throws PPMException {
        try {
            int index = 3; // ignore version number (P6) and newline character

            int[] findResult;
            findResult = getNextIntegerWithShiftedPosition(content, index); // width
            index = findResult[1];

            findResult = getNextIntegerWithShiftedPosition(content, index); // height
            index = findResult[1];

            findResult = getNextIntegerWithShiftedPosition(content, index); // depth
            index = findResult[1];

            return index;
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new PPMException(ErrorMessage.PPM_MALFORMED_FILE.getDescription());
        }
    }

    private BufferedImage createImage(int[] colors, Integer width, Integer height, Integer depth) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        if (depth > 255) {
            depth = 255;
        }

        int colorCounter = 0;
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int red = (colors[colorCounter] * 255) / depth;
                int green = (colors[colorCounter + 1] * 255) / depth;
                int blue = (colors[colorCounter + 2] * 255) / depth;

                image.setRGB(x, y, new Color(red, green, blue).getRGB());
                colorCounter += 3;
            }
        }

        return image;
    }

    private int getIntegerFromSingleDigitLine(BufferedReader br) throws PPMException {
        try {
            String part = br.readLine();
            while (part.startsWith("#")) {
                part = br.readLine();
            }

            return Integer.parseInt(part);
        } catch (NoSuchElementException | IOException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            throw new PPMException(ErrorMessage.PPM_UNEXPECTED_EOF.getDescription());
        } catch (NumberFormatException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            throw new PPMException(ErrorMessage.PPM_MALFORMED_FILE.getDescription());
        }
    }

    private int[] getIntegersFromLine(BufferedReader br) throws PPMException {
        try {
            String part = br.readLine();
            if (part == null) {
                return null;
            }

            part = part.replaceAll("[^\\S ]+", "");
            part = part.replaceAll("\\s{2,}", " ");

            while (part.trim().length() == 0 || part.startsWith("#")) {
                part = br.readLine();
            }

            String [] raw = part.split("\\s");
            int [] results = new int[raw.length];

            for (int i = 0; i < raw.length; i++) {
                try {
                    results[i] = Integer.parseInt(raw[i]);
                } catch (NumberFormatException ex) {
                    logger.log(Level.WARNING, ex.getMessage());
                    throw new PPMException(ErrorMessage.PPM_MALFORMED_FILE.getDescription());
                }
            }

            return results;
        } catch (NoSuchElementException | IOException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            throw new PPMException(ErrorMessage.PPM_UNEXPECTED_EOF.getDescription());
        } catch (NumberFormatException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            throw new PPMException(ErrorMessage.PPM_MALFORMED_FILE.getDescription());
        }
    }

    private int[] getNextIntegerWithShiftedPosition(byte [] content, int position) throws PPMException {
        int found;

        // Skip all comment lines
        boolean commentSection;
        do {
            if (content[position] == 35) {
                commentSection = true;
                position++;

                while(content[position] != 10) {
                    position++;
                }

                position++;
            } else {
                commentSection = false;
            }
        } while (commentSection);

        // Skip not-number characters
        while (content[position] < 48 || content[position] > 57) {
            position++;
        }

        StringBuilder number = new StringBuilder("" + (content[position] - 48));
        position++;
        while (content[position] >= 48 && content[position] <= 57) {
            number.append(content[position] - 48);
            position++;
        }

        try {
            found = Integer.parseInt(number.toString());
        } catch (NumberFormatException ex) {
            throw new PPMException(ErrorMessage.PPM_MALFORMED_FILE.getDescription());
        }

        return new int[] {found, position};
    }

    private byte [] readWholeFile(File file, InputStream inputStream) throws IOException {
        long fileSize = file.length();
        byte[] content = new byte[(int) fileSize];
        inputStream.read(content);

        return content;
    }

    private int unsignedToBytes(byte b) {
        return b & 0xFF;
    }
}
