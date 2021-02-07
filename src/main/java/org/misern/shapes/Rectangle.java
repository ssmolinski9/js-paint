package org.misern.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import org.misern.shapes.interfaces.Movable;
import org.misern.shapes.utils.ShapeType;
import org.misern.utils.MathUtils;

/**
 * Rectangle shape
 * @version 0.1
 * @since 0.1
 * @apiNote Freshly created rectangle will be invisible on the canvas. Remember to resize it using its parent's methods.
 * @see org.misern.shapes.interfaces.Movable
 * @see org.misern.shapes.Shape
 */
public class Rectangle extends Shape implements Movable {

    /**
     * Creates smallest rectangle as point.
     * @param start drawing start point
     */
    public Rectangle(Point start) {
        this.start = start;
        this.end = start;
    }

    public Rectangle(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Draws black rectangle in it's coords
     * @param g graphics to draw in
     * @param drawExtraPoints flag for drawing additional big ovals on the points or not
     */
    @Override
    public void draw(Graphics2D g, boolean drawExtraPoints) {
        int [] points = MathUtils.cleanRectanglePoints(start, end);
        int x0 = points[0];
        int x1 = points[1];
        int y0 = points[2];
        int y1 = points[3];

        if (isSelected) {
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g.setStroke(dashed);
        } else {
            Stroke basic = new BasicStroke(1);
            g.setStroke(basic);
        }

        drawRectangleAreaObject(g, x0, y0, x1, y1);

        if (drawExtraPoints) {
            drawPoint(g, start);
            drawPoint(g, end);
        }
    }

    @Override
    public void drawCloned(Graphics2D g, Point vec) {
        int [] points = MathUtils.cleanRectanglePoints(start, end);
        int x0 = points[0];
        int x1 = points[1];
        int y0 = points[2];
        int y1 = points[3];

        drawRectangleAreaObject(g, x0 + vec.x, y0 + vec.y, x1 + vec.x, y1 + vec.y);
    }

    protected void drawRectangleAreaObject(Graphics2D g, int x0, int y0, int x1, int y1) {
        g.drawRect(x0, y0, Math.abs(x1-x0), Math.abs(y1-y0));
    }

    /**
     * Move the rectangle with provided delta
     * @param dx horizontal number of pixels to add or subtract
     * @param dy vertical number of pixels to add or subtract
     */
    @Override
    public void move(int dx, int dy) {
        start.x += dx;
        start.y += dy;
        end.x += dx;
        end.y += dy;
    }

    @Override
    public void rotate(int xr, int yr, double angle) {
        double rotatedStartX = xr + (start.x - xr) * Math.cos(Math.toRadians(angle)) - (start.y - yr) * Math.sin(Math.toRadians(angle));
        double rotatedStartY = yr + (start.x - xr) * Math.sin(Math.toRadians(angle)) + (start.y - yr) * Math.cos(Math.toRadians(angle));

        double rotatedEndX = xr + (end.x - xr) * Math.cos(Math.toRadians(angle)) - (end.y - yr) * Math.sin(Math.toRadians(angle));
        double rotatedEndY = yr + (end.x - xr) * Math.sin(Math.toRadians(angle)) + (end.y - yr) * Math.cos(Math.toRadians(angle));

        start.x = (int) rotatedStartX;
        start.y = (int) rotatedStartY;

        end.x = (int) rotatedEndX;
        end.y = (int) rotatedEndY;
    }

    @Override
    public void drawRotated(Graphics2D g, Point p, double angle) {
        Rectangle rectangle = new Rectangle(new Point(start.x, start.y), new Point(end.x, end.y));
        rectangle.rotate(p.x, p.y, angle);
        rectangle.setColor(Color.GRAY);
        rectangle.draw(g, false);
    }

    @Override
    public void drawScaled(Graphics2D g, Point p, double dx, double dy) {
        Rectangle rectangle = new Rectangle(new Point(start.x, start.y), new Point(end.x, end.y));
        rectangle.scale(p.x, p.y, dx, dy);
        rectangle.setColor(Color.GRAY);
        rectangle.draw(g, false);
    }

    @Override
    public void scale(int xr, int yr, double sx, double sy) {
        double scaledStartX = xr + (start.x - xr) * sx;
        double scaledStartY = yr + (start.y - yr) * sy;

        double scaledEndX = xr + (end.x - xr) * sx;
        double scaledEndY = yr + (end.y - yr) * sy;

        start.x = (int) scaledStartX;
        start.y = (int) scaledStartY;

        end.x = (int) scaledEndX;
        end.y = (int) scaledEndY;
    }

    /**
     * Detects if test point is on the rectangle
     * @param point test point
     * @return true if point is on the rectangle or false if not
     */
    @Override
    public boolean isOnShape(Point point) {
        return point.x >= Math.min(start.x, end.x) && point.x <= Math.max(start.x, end.x) &&
                point.y >= Math.min(start.y, end.y) && point.y <= Math.max(start.y, end.y);
    }

    /**
     * Detects if test point is start or end drawing point of the rectangle
     * @param point test point
     * @return detected point if point is start or end drawing point (with tolerant range) or null if not
     */
    @Override
    public Point isCreationPoint(Point point) {
        if (Point2D.distance(start.x, start.y, point.x, point.y) <= MathUtils.TOLERANT_RAGE) {
            return start;
        } else if (Point2D.distance(end.x, end.y, point.x, point.y) <= MathUtils.TOLERANT_RAGE) {
            return end;
        }

        return null;
    }

    /**
     * Gets RECTANGLE as shape type
     * @return ShapeType.RECTANGLE
     */
    @Override
    public ShapeType getType() {
        return ShapeType.RECTANGLE;
    }
}
