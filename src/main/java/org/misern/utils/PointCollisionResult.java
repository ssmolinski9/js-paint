package org.misern.utils;

import java.awt.Point;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.misern.shapes.interfaces.Drawable;

/**
 * Result data of point-drawable shape collision detecting
 * @version 0.1
 * @since 0.1
 * @see CollisionDetector
 * @see java.io.Serializable
 */
@Getter
@Setter
public class PointCollisionResult implements Serializable {

    private static final long serialVersionUID = 7224043108626221498L;

    private final Drawable shape;
    private final Point point;

    /**
     * Creates result data with maximum fields
     * @param shape drawable shape detected as collided
     * @param point detected collision point
     */
    public PointCollisionResult(Drawable shape, Point point) {
        this.shape = shape;
        this.point = point;
    }
}
