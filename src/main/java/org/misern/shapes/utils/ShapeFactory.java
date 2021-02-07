package org.misern.shapes.utils;

import java.awt.Point;
import org.misern.shapes.Circle;
import org.misern.shapes.Line;
import org.misern.shapes.Rectangle;
import org.misern.shapes.Shape;

/**
 * Factory to creating shapes based on provided shape type
 * @version 0.1
 * @since 0.1
 */
public class ShapeFactory {
    /**
     * Creates shape based on its type and starting point
     * @param type type of the shape to create
     * @param start start drawing point
     * @return created drawable shape
     */
    public static Shape createShape(ShapeType type, Point start) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case LINE:
                return new Line(start);
            case RECTANGLE:
                return new Rectangle(start);
            case CIRCLE:
                return new Circle(start);
            default:
                return null;
        }
    }

    /**
     * Creates shape based on its type (provided as raw text) and starting point
     * @param type type of the shape to create (should be written with first capital character)
     * @param start start drawing point
     * @return created drawable shape
     */
    public static Shape createShape(String type, Point start) {
        switch (type) {
            case "Line":
                return createShape(ShapeType.LINE, start);
            case "Rectangle":
                return createShape(ShapeType.RECTANGLE, start);
            case "Circle":
                return createShape(ShapeType.CIRCLE, start);
            default:
                return null;
        }
    }
}
