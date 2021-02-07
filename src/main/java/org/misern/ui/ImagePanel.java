package org.misern.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RasterFormatException;
import javax.swing.JPanel;
import org.misern.utils.MathUtils;

public class ImagePanel extends JPanel {

    private BufferedImage originalImage;

    private Point selectionCoords;
    private Point selectionSize;

    private boolean drawSelection = false;
    private boolean isZooming = false;
    private boolean renderInGrayscale = false;

    private int zoomValue = 0;

    public void setImage(BufferedImage image) {
        this.originalImage = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (originalImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g.clearRect(0, 0, getWidth(), getHeight());

            int x = (this.getWidth() - originalImage.getWidth(null)) / 2;
            int y = (this.getHeight() - originalImage.getHeight(null)) / 2;

            if (renderInGrayscale) {
                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                ColorConvertOp op = new ColorConvertOp(cs, null);
                g2d.drawImage(op.filter(originalImage, null), x, y, null);
            } else {
                g2d.drawImage(originalImage, x, y, null);
            }

            if (drawSelection) {
                g2d.setColor(Color.BLACK);
                g2d.drawRect(selectionCoords.x, selectionCoords.y, selectionSize.x, selectionSize.y);

                drawSelection = false;
            }

            if (isZooming) {
                Point onImageCoords = MathUtils.getPointOnImage(originalImage, this, selectionCoords);

                if (onImageCoords.y + selectionSize.y - zoomValue > originalImage.getHeight()) {
                    zoomValue += (onImageCoords.y + selectionSize.y - zoomValue) - originalImage.getHeight();
                }

                if (selectionSize.y - zoomValue <= 1) {
                    zoomValue -= 1;
                }

                try {
                    Image subimage = originalImage.getSubimage(
                            onImageCoords.x + (zoomValue / 2),
                            onImageCoords.y + (zoomValue / 2),
                            selectionSize.x - zoomValue,
                            selectionSize.y - zoomValue);

                    subimage = subimage.getScaledInstance(selectionSize.x-1, selectionSize.y-1, Image.SCALE_DEFAULT);

                    g2d.drawImage(subimage, selectionCoords.x+1, selectionCoords.y+1, null);
                } catch (RasterFormatException ignored) {}
                finally {
                    isZooming = false;
                }
            }
        }
    }

    public void repaintOriginalImage() {
        drawSelection = false;
        repaint();
    }

    public void paintSelectionRectangle(int px, int py, int pw, int ph) {
        selectionCoords = new Point(px, py);
        selectionSize = new Point(pw, ph);
        drawSelection = true;
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public void increaseZooming() {
        if (selectionCoords != null) {
            zoomValue++;
            isZooming = true;
            drawSelection = true;
            repaint();
        }
    }

    public void decreaseZooming() {
        if (selectionCoords != null) {
            zoomValue--;
            isZooming = true;
            drawSelection = true;
            repaint();
        }
    }

    public void clearZooming() {
        isZooming = false;
        zoomValue = 0;
    }

    public void clearSelection() {
        selectionCoords = null;
        selectionSize = null;
        clearZooming();
    }

    public void setImageInGrayscale() {
        renderInGrayscale = true;
        repaint();
    }

    public void setImageInRGBMode() {
        renderInGrayscale = false;
        repaint();
    }
}
