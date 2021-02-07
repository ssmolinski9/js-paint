package org.misern.ui.colors;

import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.QuadArray;

/**
 * ColorCube with defined colors to represents RGB Cube
 * @version 0.3
 * @since 0.3
 * @see com.sun.j3d.utils.geometry.ColorCube
 */
public class RGBCube extends ColorCube {

    private static final float[] verts = new float[] {
            1.0F, -1.0F, 1.0F,      1.0F, 1.0F, 1.0F,       -1.0F, 1.0F, 1.0F,      -1.0F, -1.0F, 1.0F,
            -1.0F, -1.0F, -1.0F,    -1.0F, 1.0F, -1.0F,     1.0F, 1.0F, -1.0F,      1.0F, -1.0F, -1.0F,
            1.0F, -1.0F, -1.0F,     1.0F, 1.0F, -1.0F,      1.0F, 1.0F, 1.0F,       1.0F, -1.0F, 1.0F,
            -1.0F, -1.0F, 1.0F,     -1.0F, 1.0F, 1.0F,      -1.0F, 1.0F, -1.0F,     -1.0F, -1.0F, -1.0F,
            1.0F, 1.0F, 1.0F,       1.0F, 1.0F, -1.0F,      -1.0F, 1.0F, -1.0F,     -1.0F, 1.0F, 1.0F,
            -1.0F, -1.0F, 1.0F,     -1.0F, -1.0F, -1.0F,    1.0F, -1.0F, -1.0F,     1.0F, -1.0F, 1.0F
    };

    private static final float[] colors = new float[] {
            1.0F, 0.0F, 1.0F,       1.0F, 1.0F, 1.0F,       0.0F, 1.0F, 1.0F,       0.0F, 0.0F, 1.0F,
            0.0F, 0.0F, 0.0F,       0.0F, 1.0F, 0.0F,       1.0F, 1.0F, 0.0F,       1.0F, 0.0F, 0.0F,
            1.0F, 0.0F, 0.0F,       1.0F, 1.0F, 0.0F,       1.0F, 1.0F, 1.0F,       1.0F, 0.0F, 1.0F,
            0.0F, 0.0F, 1.0F,       0.0F, 1.0F, 1.0F,       0.0F, 1.0F, 0.0F,       0.0F, 0.0F, 0.0F,
            1.0F, 1.0F, 1.0F,       1.0F, 1.0F, 0.0F,       0.0F, 1.0F, 0.0F,       0.0F, 1.0F, 1.0F,
            0.0F, 0.0F, 1.0F,       0.0F, 0.0F, 0.0F,       1.0F, 0.0F, 0.0F,       1.0F, 0.0F, 1.0F
    };

    /**
     * Geometrical RGB cube shape
     * @param scale cube scale
     */
    public RGBCube(double scale) {
        QuadArray quadArray = new QuadArray(24, 5);
        float[] coords = new float[verts.length];

        for(int i = 0; i < verts.length; ++i) {
            coords[i] = verts[i] * (float)scale;
        }

        quadArray.setCoordinates(0, coords);
        quadArray.setColors(0, colors);
        this.setGeometry(quadArray);
    }
}
