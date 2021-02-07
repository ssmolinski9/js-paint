package org.misern.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import lombok.Setter;
import org.misern.shapes.interfaces.Movable;
import org.misern.shapes.utils.ShapeType;
import org.misern.utils.MathUtils;

/**
 * Line shape
 * @version 0.1
 * @since 0.1
 * @apiNote Freshly created line will be invisible on the canvas. Remember to resize it using its parent's methods.
 * @see org.misern.shapes.interfaces.Movable
 * @see org.misern.shapes.Shape
 */
public class Line extends Shape implements Movable {

    @Setter
    private boolean drawArrow = false;

    /**
     * Creates smallest line as point.
     * @param start drawing start point
     */
    public Line(Point start) {
        this.start = start;
        this.end = start;
    }

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Draws black line in it's coords
     * @param g graphics to draw in
     * @param drawExtraPoints flag for drawing additional big ovals on the points or not
     */
    @Override
    public void draw(Graphics2D g, boolean drawExtraPoints) {
        if (isSelected) {
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g.setStroke(dashed);
        } else {
            Stroke basic = new BasicStroke(1);
            g.setStroke(basic);
        }

        if (drawArrow) {
            int d = 7;
            int h = 7;
            int x1 = start.x;
            int x2 = end.x;
            int y1 = start.y;
            int y2 = end.y;

            int dx = x2 - x1, dy = y2 - y1;
            double D = Math.sqrt(dx*dx + dy*dy);
            double xm = D - d, xn = xm, ym = h, yn = -h, x;
            double sin = dy / D, cos = dx / D;

            x = xm*cos - ym*sin + x1;
            ym = xm*sin + ym*cos + y1;
            xm = x;

            x = xn*cos - yn*sin + x1;
            yn = xn*sin + yn*cos + y1;
            xn = x;

            int[] xpoints = {x2, (int) xm, (int) xn};
            int[] ypoints = {y2, (int) ym, (int) yn};

            g.drawLine(x1, y1, x2, y2);
            g.fillPolygon(xpoints, ypoints, 3);
        } else {
            g.drawLine(start.x, start.y, end.x, end.y);
        }

        if (drawExtraPoints) {
            drawPoint(g, start);
            drawPoint(g, end);
        }
    }

    @Override
    public void drawCloned(Graphics2D g, Point vec) {
        g.setColor(Color.GRAY);
        g.drawLine(start.x + vec.x, start.y + vec.y, end.x + vec.x, end.y + vec.y);
    }

    /**
     * Move the line with provided delta
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
        Line line = new Line(new Point(start.x, start.y), new Point(end.x, end.y));
        line.rotate(p.x, p.y, angle);
        line.setColor(Color.GRAY);
        line.draw(g, false);
    }

    @Override
    public void drawScaled(Graphics2D g, Point p, double dx, double dy) {
        Line line = new Line(new Point(start.x, start.y), new Point(end.x, end.y));
        line.scale(p.x, p.y, dx, dy);
        line.setColor(Color.GRAY);
        line.draw(g, false);
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
     * Detects if test point is on the line
     * @param point test point
     * @return true if point is on the line or false if not
     */
    @Override
    public boolean isOnShape(Point point) {
        return MathUtils.isPointOnLine(this, point);
    }

    /**
     * Detects if test point is start or end drawing point of the line
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
     * Gets LINE as shape type
     * @return ShapeType.LINE
     */
    @Override
    public ShapeType getType() {
        return ShapeType.LINE;
    }
}
