package org.misern.shapes.interfaces;

import java.awt.Point;

/**
 * Movable shape definition
 * @version 0.7
 * @since 0.1
 */
public interface Movable {
    /**
     * Move the shape with provided delta
     * @param dx horizontal number of pixels to add or subtract
     * @param dy vertical number of pixels to add or subtract
     */
    void move(int dx, int dy);

    void rotate(int xr, int yr, double angle);

    void scale(int xr, int yr, double sx, double sy);

    /**
     * Detects if test point is on the shape
     * @param point test point
     * @return true if point is on the shape or false if not
     */
    boolean isOnShape(Point point);
}
