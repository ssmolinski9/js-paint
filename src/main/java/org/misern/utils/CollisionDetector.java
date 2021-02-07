package org.misern.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.misern.shapes.Shape;
import org.misern.shapes.interfaces.Drawable;
import org.misern.shapes.interfaces.Movable;

/**
 * Collision detector for recognising shapes and points detections
 * @version 0.1
 * @since 0.1
 */
public class CollisionDetector {

    /**
     * Gets top shape from the list that collides with provided point
     * @param shapes list of shapes that possibly can be touched by point
     * @param point test point
     * @return result element with information about shape and nearest detected point (based on tolerant) or null if there's no collided shape
     */
    public static PointCollisionResult getDetectedPoint(List<Shape> shapes, Point point) {
        List<Drawable> resizableShapes = new ArrayList<>(shapes);
        Collections.reverse(resizableShapes);

        for (Drawable shape : resizableShapes) {
            Point detected = shape.isCreationPoint(point);
            if (detected != null) {
                return new PointCollisionResult(shape, detected);
            }
        }

        return null;
    }

    /**
     * Gets detected movable shape that collided with point
     * @param shapes list of shapes that possibly can be touched by point
     * @param point test point
     * @return movable collided shape or null if there's no collided shape
     */
    public static Movable getDetectedShape(List<Shape> shapes, Point point) {
        for (Movable shape : getMovableShapes(shapes)) {
            if (shape.isOnShape(point)) {
                return shape;
            }
        }

        return null;
    }

    /**
     * Gets only movable shapes from the list of shapes
     * @param shapes list of shapes
     * @return list of movable shapes organised from top to bottom shapes
     */
    private static List<Movable> getMovableShapes(List<Shape> shapes) {
        List<Movable> movableShapes = new ArrayList<>();
        for (Shape shape : shapes) {
            if (shape instanceof Movable) {
                movableShapes.add((Movable) shape);
            }
        }

        Collections.reverse(movableShapes);
        return movableShapes;
    }
}
