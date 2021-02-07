package org.misern;

import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import org.misern.handler.analyzer.ShowAnalyzerFrameActionHandler;
import org.misern.handler.binary.ShowBinarizationFrameActionHandler;
import org.misern.handler.morphology.ShowMorphologyFrameActionHandler;
import org.misern.handler.drawing.DrawShapeActionHandler;
import org.misern.handler.drawing.InsertPointActionHandler;
import org.misern.handler.drawing.InsertShapeActionHandler;
import org.misern.handler.drawing.MoveShapeActionHandler;
import org.misern.handler.drawing.RealTimeRotatingShapeActionHandler;
import org.misern.handler.drawing.RealTimeScalingShapeActionHandler;
import org.misern.handler.drawing.RealTimeVectorMoveShapeActionHandler;
import org.misern.handler.drawing.ResizeShapeActionHandler;
import org.misern.handler.drawing.SelectShapeActionHandler;
import org.misern.handler.drawing.StartRotatingShapeActionHandler;
import org.misern.handler.drawing.StartScalingShapeActionHandler;
import org.misern.handler.drawing.StartVectorMoveShapeActionHandler;
import org.misern.handler.filters.AddPixelsActionHandler;
import org.misern.handler.filters.DarkenImageActionHandler;
import org.misern.handler.filters.EqualizeFilterActionHandler;
import org.misern.handler.filters.GaussFilterActionHandler;
import org.misern.handler.filters.GeneralFilterActionHandler;
import org.misern.handler.filters.GrayscaleImageActionHandler;
import org.misern.handler.filters.MedianFilterActionHandler;
import org.misern.handler.filters.MultiplyPixelsActionHandler;
import org.misern.handler.filters.SharpenFilterActionHandler;
import org.misern.handler.filters.SobelAlgorithmActionHandler;
import org.misern.handler.filters.SubtractPixelsActionHandler;
import org.misern.handler.general.CoordsInformationActionHandler;
import org.misern.handler.general.ExitActionHandler;
import org.misern.handler.general.NewFileActionHandler;
import org.misern.handler.general.OpenFileActionHandler;
import org.misern.handler.general.SaveFileActionHandler;
import org.misern.handler.histogram.ShowHistogramFrameActionHandler;
import org.misern.handler.tools.ColorPickerOpenActionHandler;
import org.misern.handler.tools.RGBCubeOpenActionHandler;
import org.misern.handler.tools.ShapeTypeChangeActionHandler;
import org.misern.handler.tools.ToolTypeChangeActionHandler;
import org.misern.handler.transforms.RotateShapeActionHandler;
import org.misern.handler.transforms.ScaleShapeActionHandler;
import org.misern.handler.transforms.VectorMoveShapeActionHandler;
import org.misern.ui.InputPanel;
import org.misern.ui.PaintPanel;

/**
 * Starting point for the application
 * @author Sebastian Smoliński
 */
public final class Application {

    private final MainFrame frame;
    private final InputPanel inputPanel;
    private final PaintPanel paintPanel;

    public Application() {
        frame = new MainFrame();
        inputPanel = frame.getInputPanel();
        paintPanel = frame.getPaintPanel();

        System.setProperty("sun.awt.noerasebackground", "true");
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager ttm = ToolTipManager.sharedInstance();
        ttm.setLightWeightPopupEnabled(false);

        initEventHandlers();
    }

    private void initEventHandlers() {
        inputPanel.getLine().addActionListener(new ShapeTypeChangeActionHandler(frame));
        inputPanel.getRectangle().addActionListener(new ShapeTypeChangeActionHandler(frame));
        inputPanel.getCircle().addActionListener(new ShapeTypeChangeActionHandler(frame));
        inputPanel.getPolygon().addActionListener(new ShapeTypeChangeActionHandler(frame));
        inputPanel.getMove().addActionListener(new ToolTypeChangeActionHandler(frame));
        inputPanel.getResize().addActionListener(new ToolTypeChangeActionHandler(frame));
        inputPanel.getSelect().addActionListener(new ToolTypeChangeActionHandler(frame));
        inputPanel.getColor().addActionListener(new ColorPickerOpenActionHandler(frame));
        inputPanel.getRgbCube().addActionListener(new RGBCubeOpenActionHandler(frame));
        inputPanel.getMoveVec().addActionListener(new StartVectorMoveShapeActionHandler(frame));
        inputPanel.getRotate().addActionListener(new StartRotatingShapeActionHandler(frame));
        inputPanel.getScale().addActionListener(new StartScalingShapeActionHandler(frame));

        paintPanel.addMouseMotionListener(new CoordsInformationActionHandler(frame));

        paintPanel.addMouseMotionListener(new RealTimeVectorMoveShapeActionHandler(frame));
        paintPanel.addMouseListener(new RealTimeVectorMoveShapeActionHandler(frame));

        paintPanel.addMouseMotionListener(new RealTimeRotatingShapeActionHandler(frame));
        paintPanel.addMouseListener(new RealTimeRotatingShapeActionHandler(frame));

        paintPanel.addMouseMotionListener(new RealTimeScalingShapeActionHandler(frame));
        paintPanel.addMouseListener(new RealTimeScalingShapeActionHandler(frame));

        paintPanel.addMouseMotionListener(new DrawShapeActionHandler(frame));
        paintPanel.addMouseListener(new DrawShapeActionHandler(frame));
        paintPanel.addMouseMotionListener(new MoveShapeActionHandler(frame));
        paintPanel.addMouseListener(new MoveShapeActionHandler(frame));
        paintPanel.addMouseMotionListener(new ResizeShapeActionHandler(frame));
        paintPanel.addMouseListener(new ResizeShapeActionHandler(frame));
        paintPanel.addMouseListener(new SelectShapeActionHandler(frame));

        frame.getLine().addActionListener(new InsertShapeActionHandler(frame));
        frame.getCircle().addActionListener(new InsertShapeActionHandler(frame));
        frame.getRectangle().addActionListener(new InsertShapeActionHandler(frame));
        frame.getPoint().addActionListener(new InsertPointActionHandler(frame));

        frame.getNewFile().addActionListener(new NewFileActionHandler(frame));
        frame.getSaveFile().addActionListener(new SaveFileActionHandler(frame));
        frame.getOpenFile().addActionListener(new OpenFileActionHandler(frame));
        frame.getExit().addActionListener(new ExitActionHandler(frame));

        frame.getAdd().addActionListener(new AddPixelsActionHandler(frame));
        frame.getSubtract().addActionListener(new SubtractPixelsActionHandler(frame));
        frame.getMultiply().addActionListener(new MultiplyPixelsActionHandler(frame));
        frame.getDivide().addActionListener(new MultiplyPixelsActionHandler(frame));
        frame.getDarken().addActionListener(new DarkenImageActionHandler(frame));
        frame.getGrayscale().addActionListener(new GrayscaleImageActionHandler(frame));
        frame.getEqualize().addActionListener(new EqualizeFilterActionHandler(frame));
        frame.getBlur().addActionListener(new GaussFilterActionHandler(frame));
        frame.getMedian().addActionListener(new MedianFilterActionHandler(frame));
        frame.getSobel().addActionListener(new SobelAlgorithmActionHandler(frame));
        frame.getSharpen().addActionListener(new SharpenFilterActionHandler(frame));
        frame.getFilter().addActionListener(new GeneralFilterActionHandler(frame));

        frame.getTMove().addActionListener(new VectorMoveShapeActionHandler(frame));
        frame.getTRotate().addActionListener(new RotateShapeActionHandler(frame));
        frame.getTScale().addActionListener(new ScaleShapeActionHandler(frame));

        frame.getHistograms().addActionListener(new ShowHistogramFrameActionHandler(frame.getPaintPanel()));
        frame.getBinarization().addActionListener(new ShowBinarizationFrameActionHandler(frame.getPaintPanel()));
        frame.getMorphology().addActionListener(new ShowMorphologyFrameActionHandler(frame.getPaintPanel()));
        frame.getAnalyzer().addActionListener(new ShowAnalyzerFrameActionHandler(frame.getPaintPanel()));
    }

    public static void main(String[] args) {
        new Application();
    }
}
