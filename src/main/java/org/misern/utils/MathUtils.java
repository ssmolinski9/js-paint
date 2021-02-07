package org.misern.utils;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.misern.histogram.Histogram;
import org.misern.shapes.Line;

/**
 * Tools for geo calculations
 * @since 0.1
 * @version 0.2
 */
public class MathUtils {

    public static Integer TOLERANT_RAGE = 5;

    /**
     * Checks if given point is on given line with tolerant rage
     * @param line root line
     * @param point root point
     * @return true if point is on the line or false if not
     */
    public static boolean isPointOnLine(Line line, Point point) {
        Point start = line.getStart();
        Point end = line.getEnd();

        for (int i = -TOLERANT_RAGE; i <= TOLERANT_RAGE; i++) {
            for (int j = -TOLERANT_RAGE; j <= TOLERANT_RAGE; j++) {
                if (Line2D.ptSegDist(start.x, start.y, end.x, end.y, point.x + j, point.y + i) == 0.0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Clean points for drawing an rectangle that start point will always be top left corner and end point will always be bottom right corner
     * @param start start drawing point
     * @param end end drawing point
     * @return cleaned points inside array [x0, x1, y0, y1], start point: (x0, y0), end point: (x1, y1)
     */
    public static int[] cleanRectanglePoints(Point start, Point end) {
        int [] points = new int[4];
        points[0] = start.x;
        points[1] = end.x;
        points[2] = start.y;
        points[3] = end.y;

        if (start.x >= end.x && start.y <= end.y) {
            points[0] = start.x - Math.abs(end.x-start.x);
            points[1] = end.x + Math.abs(end.x-start.x);
        } else if (start.x <= end.x && start.y >= end.y) {
            points[2] = start.y - Math.abs(end.y-start.y);
            points[3] = end.y + Math.abs(end.y-start.y);
        } else if (start.x >= end.x) {
            points[0] = start.x - Math.abs(end.x-start.x);
            points[1] = end.x + Math.abs(end.x-start.x);
            points[2] = start.y - Math.abs(end.y-start.y);
            points[3] = end.y + Math.abs(end.y-start.y);
        }

        return points;
    }

    /**
     * Calculates distance between two points
     * @param p1 first point
     * @param p2 second point
     * @return distance in double
     */
    public static double calculateDistanceBetweenPoints(Point p1, Point p2) {
        return Math.sqrt((p2.y - p1.y) * (p2.y - p1.y) + (p2.x - p1.x) * (p2.x - p1.x));
    }

    /**
     * Calculates starting point from image should be rendered (top left corner) if the image should be centered in given canvas
     * @since 0.2
     * @param canvasWidth pixels in canvas' width
     * @param canvasHeight pixels in canvas' height
     * @param imageWidth pixels in image's width
     * @param imageHeight pixels in image's height
     * @return top left point from centered image should be rendered
     */
    public static Point calculateImageCenteredStartPoint(int canvasWidth, int canvasHeight, int imageWidth, int imageHeight) {
        Point point = new Point();
        point.x = (canvasWidth - imageWidth) / 2;
        point.y = (canvasHeight - imageHeight) / 2;

        return point;
    }

    public static Point getPointOnImage(BufferedImage image, JPanel imagePanel, Point selectedPoint) {
        Point imgLocation = getImageLocation(image, imagePanel.getWidth(), imagePanel.getHeight());

        Point relative = new Point(selectedPoint);
        relative.x -= imgLocation.x;
        relative.y -= imgLocation.y;

        return relative;
    }

    private static Point getImageLocation(BufferedImage img, Integer panelWidth, Integer panelHeight) {
        Point p = null;
        if (img != null) {
            int x = (panelWidth - img.getWidth()) / 2;
            int y = (panelHeight - img.getHeight()) / 2;
            p = new Point(x, y);
        }

        return p;
    }

    public static Integer getOtsuThreshold(BufferedImage image) {
        Histogram histograms = new Histogram(image);
        double[] histogram = histograms.getHistogram().get(Histogram.CHANNELS.RED);

        int imagePixels = image.getHeight() * image.getWidth();

        float sum = 0;
        for (int px = 0; px < 256; px++) {
            sum += px * histogram[px];
        }

        float sumB = 0;
        int wF, wB = 0;

        float varMax = 0;
        int threshold = 0;

        for (int t = 0; t < 256; t++) {
            wB += histogram[t];
            if (wB == 0) continue;

            wF = imagePixels - wB;
            if (wF == 0) break;

            sumB += (float) (t * histogram[t]);

            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = t;
            }
        }

        return threshold;
    }

    public static double calculateCoeff(int n, int k) {
        long value = 1;

        for (int i = 1; i <= k; i++) {
            value *= (double) (n - i + 1) / i;
        }

        return value;
    }

    public static double getPointsAngle(Point a, Point b) {
        return Math.toDegrees(Math.atan2((b.y - a.y), (b.x - a.x)));
    }
}
