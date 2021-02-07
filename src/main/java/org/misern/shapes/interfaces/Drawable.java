package org.misern.shapes.interfaces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import org.misern.shapes.utils.ShapeType;

/**
 * Drawable shape
 * @version 0.3
 * @since 0.1
 */
public interface Drawable {
    /**
     * Draws shape inside graphics
     * @param g graphics element to draw shape inside
     * @param drawExtraPoints flag for drawing additional big ovals on the points or not
     */
    void draw(Graphics2D g, boolean drawExtraPoints);

    void drawCloned(Graphics2D g, Point vec);

    void drawRotated(Graphics2D g, Point p, double angle);

    void drawScaled(Graphics2D g, Point p, double dx, double dy);

    /**
     * Resize a shape by moving its end drawing point
     * @param resized new end point
     */
    void resize(Point resized);

    /**
     * Resize shape by change given point
     * @param before point before resize action
     * @param after new drawing point
     * @return changed point or null if there's no such point
     */
    Point resizeByAnyPoint(Point before, Point after);

    /**
     * Detects if test point is start or end drawing point of the shape
     * @param point test point
     * @return detected point if point is start or end drawing point (with tolerant range) or null if not
     */
    Point isCreationPoint(Point point);

    /**
     * Gets shape type
     * @return drawable shape type
     */
    ShapeType getType();

    /**
     * Gets shape color
     * @return shape color
     * @since 0.3
     */
    Color getColor();
}
