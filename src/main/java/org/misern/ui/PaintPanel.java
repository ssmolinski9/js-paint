package org.misern.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;
import org.misern.shapes.Line;
import org.misern.shapes.Polygon;
import org.misern.shapes.Shape;
import org.misern.shapes.interfaces.Drawable;
import org.misern.shapes.interfaces.Movable;
import org.misern.utils.CollisionDetector;
import org.misern.utils.MathUtils;
import org.misern.utils.PointCollisionResult;

/**
 * Painting canvas for geometrical shapes or images
 * @see Drawable
 * @see Movable
 * @see BufferedImage
 * @since 0.1
 * @version 0.3
 */
public class PaintPanel extends JPanel {

    private final List<Shape> shapes = new ArrayList<>();

    @Getter
    @Setter
    private Point movingStartPoint;

    @Getter
    @Setter
    private Point resizingStartPoint;

    @Getter
    @Setter
    private boolean isDrawing = false;

    @Getter
    @Setter
    private boolean isMoving = false;

    @Getter
    @Setter
    private boolean isResizing = false;

    @Setter
    private boolean drawExtraPoints = false;

    @Getter
    @Setter
    private Drawable drawingShape = null;

    @Getter
    @Setter
    private Drawable resizingShape = null;

    @Getter
    @Setter
    private Movable movingShape = null;

    @Getter
    @Setter
    private boolean movingVector = false;

    @Getter
    @Setter
    private boolean rotating = false;

    @Getter
    @Setter
    private boolean scaling = false;

    @Setter
    @Getter
    private Point movingVectorPoint = null;

    @Getter
    @Setter
    private Point rotatingStartPoint = null;

    @Getter
    @Setter
    private Point rotatingActualPoint = null;

    @Getter
    @Setter
    private Point scalingStartPoint = null;

    @Getter
    @Setter
    private Point scalingActualPoint = null;

    @Getter
    @Setter
    private Shape selectedShape = null;

    @Getter
    @Setter
    private BufferedImage image = null;

    @Getter
    @Setter
    private Color selectedColor = Color.BLACK;

    @Getter
    @Setter
    private int bezierPoints = 0;

    public PaintPanel() {
        setBackground(Color.WHITE);
    }

    /**
     * Prints drawable content starting with loaded image, going through the list of shapes
     * (shape earliest added will be at the bottom of content)
     * @param g graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());

        if (image != null) {
            Point startPoint = MathUtils.calculateImageCenteredStartPoint(
                    getWidth(),
                    getHeight(),
                    image.getWidth(),
                    image.getHeight()
            );

            g.drawImage(image, startPoint.x, startPoint.y, null);
        }

        for(Drawable shape : shapes) {
            if (selectedShape != null && movingVector && selectedShape == shape) {
                if (selectedShape instanceof Polygon) {
                    Line line = new Line(((Polygon) selectedShape).getPoints().get(0), movingVectorPoint);
                    line.setDrawArrow(true);
                    line.draw((Graphics2D) g, false);

                    Point vec = new Point(movingVectorPoint.x - ((Polygon) selectedShape).getPoints().get(0).x,
                            movingVectorPoint.y - ((Polygon) selectedShape).getPoints().get(0).y);
                    shape.drawCloned((Graphics2D) g, vec);
                } else {
                    Line line = new Line(selectedShape.getStart(), movingVectorPoint);
                    line.setDrawArrow(true);
                    line.draw((Graphics2D) g, false);

                    Point vec = new Point(movingVectorPoint.x - selectedShape.getStart().x,
                            movingVectorPoint.y - selectedShape.getStart().y);
                    shape.drawCloned((Graphics2D) g, vec);
                }
            }

            if (selectedShape != null && rotating && selectedShape == shape && rotatingActualPoint != null && rotatingStartPoint != null) {
                if (selectedShape instanceof Polygon) {
                    double angle = MathUtils.getPointsAngle(((Polygon) selectedShape).getPoints().get(0), rotatingActualPoint);
                    selectedShape.drawRotated((Graphics2D) g, rotatingStartPoint, angle);
                } else {
                    double angle = MathUtils.getPointsAngle(selectedShape.getStart(), rotatingActualPoint);
                    selectedShape.drawRotated((Graphics2D) g, rotatingStartPoint, angle);
                }
            }

            if (selectedShape != null && scaling && selectedShape == shape && scalingActualPoint != null && scalingStartPoint != null) {
                if (selectedShape instanceof Polygon) {
                    double dx = Math.abs(((Polygon) getSelectedShape()).getPoints().get(0).x - getScalingActualPoint().x) * 0.01;
                    double dy = Math.abs(((Polygon) getSelectedShape()).getPoints().get(0).y - getScalingActualPoint().y) * 0.01;
                    selectedShape.drawScaled((Graphics2D) g, scalingStartPoint, dx, dy);
                } else {
                    double dx = Math.abs(getSelectedShape().getStart().x - getScalingActualPoint().x) * 0.01;
                    double dy = Math.abs(getSelectedShape().getStart().y - getScalingActualPoint().y) * 0.01;
                    selectedShape.drawScaled((Graphics2D) g, scalingStartPoint, dx, dy);
                }
            }

            g.setColor(shape.getColor());
            shape.draw((Graphics2D)g, drawExtraPoints);
        }
    }

    /**
     * Gets shape detected in given point area
     * @param point place at the canvas where shape will be searching
     * @return detected movable object or null if there is no object in given point
     */
    public Movable getDetectedShape(Point point) {
        return CollisionDetector.getDetectedShape(shapes, point);
    }

    /**
     * Gets shape detected in given point area
     * @param point place at the canvas where shape will be searching
     * @return detected drawable object or null if there is no object in given point
     */
    public Drawable getDetectedPoint(Point point) {
        PointCollisionResult detectedResults = CollisionDetector.getDetectedPoint(shapes, point);
        if (detectedResults != null) {
            setResizingStartPoint(detectedResults.getPoint());
            return detectedResults.getShape();
        }

        return null;
    }

    /**
     * Add new shape to draw
     * @param shape shape to draw
     * @see Shape
     */
    public void addShape(Shape shape) {
        shape.setColor(this.selectedColor);
        this.shapes.add(shape);
    }

    /**
     * Clear the canvas
     */
    public void clearPaintPanel() {
        this.shapes.clear();
        this.image = null;
    }

    /**
     * Remove the last added shape from the image
     */
    public void removeLastShape() {
        this.shapes.remove(this.shapes.size()-1);
        repaint();
    }
}
