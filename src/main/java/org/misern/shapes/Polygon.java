package org.misern.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.misern.shapes.interfaces.Movable;
import org.misern.shapes.utils.ShapeType;
import org.misern.utils.MathUtils;

/**
 * Polygon shape
 * @version 0.5
 * @since 0.1
 * @see org.misern.shapes.Shape
 */
public final class Polygon extends Shape implements Movable {

    private final List<Point> points = new ArrayList<>();
    private final int degree;

    public Polygon(int degree) {
        this.degree = degree;
    }

    /**
     * Draws black polygon from its points
     * @param g graphics to draw in
     * @param drawExtraPoints flag for drawing additional big ovals on the points or not
     */
    @Override
    public void draw(Graphics2D g, boolean drawExtraPoints) {
        for (Point point : points) {
            drawPoint(g, point);
        }

        if (isSelected) {
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g.setStroke(dashed);
        } else {
            Stroke basic = new BasicStroke(1);
            g.setStroke(basic);
        }

        if (points.size() == degree) {
            g.drawPolygon(points.stream().mapToInt(point -> point.x).toArray(),
                    points.stream().mapToInt(point -> point.y).toArray(),
                    points.size());
        }
    }

    @Override
    public void drawCloned(Graphics2D g, Point vec) {
        for (Point point : points) {
            drawPoint(g, new Point(point.x + vec.x, point.y + vec.y));
        }

        g.drawPolygon(points.stream().mapToInt(point -> point.x + vec.x).toArray(),
                points.stream().mapToInt(point -> point.y + vec.y).toArray(),
                points.size());
    }

    /**
     * Resize polygon by change given point
     * @param before point before resize action
     * @param after new drawing point
     * @return changed point or null if there's no such point
     */
    @Override
    public Point resizeByAnyPoint(Point before, Point after) {
        for (Point point : points) {
            if (point.x == before.x && point.y == before.y) {
                point.setLocation(after);
                return point;
            }
        }

        return null;
    }

    /**
     * Add new point of the polygon
     * @param point point of the polygon for draw as last
     */
    public void addPoint(Point point) {
        if (points.size() < degree) {
            points.add(point);
        }
    }

    /**
     * Update point which is nearest of the provided
     * @param p test point for find existing point nearest to this
     * @param updated point with new coords
     */
    public void updatePoint(Point p, Point updated) {
        Point nearest = getNearest(p);
        nearest.x = updated.x;
        nearest.y = updated.y;
    }

    /**
     * Detects if test point is any of the polygon's points
     * @param point test point
     * @return detected polygon's point if it is in the tolerant range or null there's no point in range of test point
     */
    @Override
    public Point isCreationPoint(Point point) {
        for (Point polygonPoint : points) {
            if (Point2D.distance(polygonPoint.x, polygonPoint.y, point.x, point.y) <= MathUtils.TOLERANT_RAGE) {
                return polygonPoint;
            }
        }

        return null;
    }

    /**
     * Get POLYGON as shape type
     * @return ShapeType.POLYGON
     */
    @Override
    public ShapeType getType() {
        return ShapeType.POLYGON;
    }

    /**
     * Find nearest point to the selected
     * @param selected test point
     * @return nearest point of the polygon
     */
    public Point getNearest(Point selected) {
        Point nearest = points.get(0);
        for (Point point : points) {
            if (MathUtils.calculateDistanceBetweenPoints(point, selected) <
                    MathUtils.calculateDistanceBetweenPoints(nearest, selected)) {
                nearest = point;
            }
        }

        return nearest;
    }

    @Override
    public void move(int dx, int dy) {
        for (Point point : points) {
            point.x += dx;
            point.y += dy;
        }
    }

    @Override
    public void rotate(int xr, int yr, double angle) {
        for (Point point : points) {
            double rotatedX = xr + (point.x - xr) * Math.cos(Math.toRadians(angle)) - (point.y - yr) * Math.sin(Math.toRadians(angle));
            double rotatedY = yr + (point.x - xr) * Math.sin(Math.toRadians(angle)) + (point.y - yr) * Math.cos(Math.toRadians(angle));

            point.x = (int) rotatedX;
            point.y = (int) rotatedY;
        }
    }

    @Override
    public void drawRotated(Graphics2D g, Point p, double angle) {
        Polygon polygon = new Polygon(degree);
        for (Point point : points) {
            polygon.addPoint(new Point(point.x, point.y));
        }

        polygon.rotate(p.x, p.y, angle);
        polygon.setColor(Color.GRAY);
        polygon.draw(g, false);
    }

    @Override
    public void drawScaled(Graphics2D g, Point p, double dx, double dy) {
        Polygon polygon = new Polygon(degree);
        for (Point point : points) {
            polygon.addPoint(new Point(point.x, point.y));
        }

        polygon.scale(p.x, p.y, dx, dy);
        polygon.setColor(Color.GRAY);
        polygon.draw(g, false);
    }

    @Override
    public void scale(int xr, int yr, double sx, double sy) {
        for (Point point : points) {
            double scaledX = xr + (point.x - xr) * sx;
            double scaledY = yr + (point.y - yr) * sy;

            point.x = (int) scaledX;
            point.y = (int) scaledY;
        }
    }

    @Override
    public boolean isOnShape(Point point) {
        return (isCreationPoint(point) != null);
    }

    public List<Point> getPoints() {
        return points;
    }
}
