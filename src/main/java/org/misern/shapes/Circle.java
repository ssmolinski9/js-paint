package org.misern.shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import org.misern.shapes.interfaces.Movable;
import org.misern.shapes.utils.ShapeType;

/**
 * Circle shape
 * @version 0.1
 * @since 0.1
 * @apiNote Circle has the same behaviour as Rectangle. Using Swing API — circle in fact is drawing as circle inside rectangle.
 * @see org.misern.shapes.interfaces.Movable
 * @see org.misern.shapes.Rectangle
 */
public class Circle extends Rectangle implements Movable {

    /**
     * Creates smallest circle as point.
     * @param start drawing start point
     */
    public Circle(Point start) {
        super(start);
    }

    @Override
    protected void drawRectangleAreaObject(Graphics2D g, int x0, int y0, int x1, int y1) {
        g.drawOval(x0, y0, Math.abs(x1-x0), Math.abs(y1-y0));
    }

    /**
     * Gets CIRCLE as shape type
     * @return ShapeType.CIRCLE
     */
    @Override
    public ShapeType getType() {
        return ShapeType.CIRCLE;
    }
}
