package org.misern.ui.colors;

import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;

/**
 * Panel with RGB cube displayed
 * @version 0.3
 * @since 0.3
 * @see javax.swing.JPanel
 */
public class RGBCubePanel extends JPanel {

    private double angleY = 0.0;
    private double angleX = 0.0;

    private final Transform3D rotationX = new Transform3D();
    private final Transform3D rotationY = new Transform3D();

    /**
     * Creates JPanel filled with colored cube
     */
    public RGBCubePanel() {
        RGBCube colorCube = new RGBCube(0.25f);
        colorCube.getGeometry();

        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        SimpleUniverse universe = new SimpleUniverse(canvas);
        BranchGroup group = new BranchGroup();
        TransformGroup tGroup = new TransformGroup();
        tGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        group.addChild(tGroup);
        tGroup.addChild(colorCube);
        add("Center", canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.getViewer().getView().setBackClipDistance(100.0);
        universe.addBranchGraph(group);

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_A) {
                    angleY -= 0.1;
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    angleY += 0.1;
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    angleX -= 0.1;
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    angleX += 0.1;
                }

                rotationY.rotY(angleY);
                rotationX.rotX(angleX);

                rotationX.mul(rotationY);
                tGroup.setTransform(rotationX);
            }
        });
    }
}
