package org.misern.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.misern.shapes.interfaces.Drawable;

/**
 * General drawable on the canvas geometrical shape
 * @version 0.3
 * @since 0.1
 * @see org.misern.shapes.interfaces.Drawable
 * @see java.io.Serializable
 */
@Getter
public abstract class Shape implements Drawable, Serializable {

    private static final long serialVersionUID = 3702926154515159432L;

    protected Point start;
    protected Point end;
    protected Color color = Color.BLACK;

    @Setter
    protected boolean isSelected = false;

    /**
     * Resize a shape by moving its end drawing point
     * @see Drawable
     * @param resized new end point
     */
    public void resize(Point resized) {
        this.end = resized;
    }

    /**
     * Updates start and end drawing point
     * @param x0 start drawing point x
     * @param y0 start drawing point y
     * @param x1 end drawing point x
     * @param y1 end drawing point y
     */
    public void updatePoints(int x0, int y0, int x1, int y1) {
        getStart().x = x0;
        getStart().y = y0;
        getEnd().x = x1;
        getEnd().y = y1;
    }

    /**
     * Resize shape by changing start either end drawing point
     * @param before point before resize action
     * @param after new drawing point
     * @return changed point or null if there's no point such a provided before point
     */
    @Override
    public Point resizeByAnyPoint(Point before, Point after) {
        if (start.x == before.x && start.y == before.y) {
            this.start = after;
            return this.start;
        } else if (end.x == before.x && end.y == before.y) {
            this.end = after;
            return this.end;
        }

        return null;
    }

    /**
     * Draws big oval point on the shape
     * @param g graphics object
     * @param point point to draw additional big oval
     * @see Graphics2D
     */
    protected void drawPoint(Graphics2D g, Point point) {
        int x0 = point.x - 2;
        int y0 = point.y - 2;

        g.drawOval(x0, y0, 4, 4);
    }

    /**
     * Sets shape color
     * @param color color to draw shape in
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
