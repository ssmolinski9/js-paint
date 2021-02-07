package org.misern;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import lombok.Getter;
import lombok.Setter;
import org.misern.shapes.utils.ShapeType;
import org.misern.tools.ToolType;
import org.misern.ui.InputPanel;
import org.misern.ui.PaintPanel;

/**
 * Main gui frame for the application's content
 * @version 0.4
 * @since 0.1
 */
@Getter
public class MainFrame extends JFrame {

    private final JMenuItem newFile = new JMenuItem("New");
    private final JMenuItem openFile = new JMenuItem("Load");
    private final JMenuItem saveFile = new JMenuItem("Save");
    private final JMenuItem exit = new JMenuItem("Exit");

    private final JMenuItem line = new JMenuItem("Line");
    private final JMenuItem rectangle = new JMenuItem("Rectangle");
    private final JMenuItem circle = new JMenuItem("Circle");
    private final JMenuItem point = new JMenuItem("Add point");

    private final JMenuItem tMove = new JMenuItem("Move");
    private final JMenuItem tRotate = new JMenuItem("Rotate");
    private final JMenuItem tScale = new JMenuItem("Scale");

    private final JMenuItem add = new JMenuItem("Add pixels");
    private final JMenuItem subtract = new JMenuItem("Subtract pixels");
    private final JMenuItem multiply = new JMenuItem("Multiply pixels");
    private final JMenuItem divide = new JMenuItem("Divide pixels");
    private final JMenuItem darken = new JMenuItem("Darken image");

    private final JMenuItem grayscale = new JMenuItem("Convert to grayscale");
    private final JMenuItem equalize = new JMenuItem("Equalize");
    private final JMenuItem sharpen = new JMenuItem("Sharpen");
    private final JMenuItem blur = new JMenuItem("Blur");
    private final JMenuItem median = new JMenuItem("Median filter");
    private final JMenuItem sobel = new JMenuItem("Sobel's algorithm");
    private final JMenuItem filter = new JMenuItem("General filter");

    private final JMenuItem histograms = new JMenuItem("Histograms");
    private final JMenuItem binarization = new JMenuItem("Binarize");
    private final JMenuItem morphology = new JMenuItem("Morphology");
    private final JMenuItem analyzer = new JMenuItem("Analyzer");

    private final JLabel coords = new JLabel("X: -, Y: -");

    private final PaintPanel paintPanel = new PaintPanel();
    private final InputPanel inputPanel = new InputPanel();

    @Setter
    private ShapeType selectedShapeType;

    @Setter
    private ToolType selectedToolType;

    /**
     * Creates and show main frame
     */
    public MainFrame() {
        setLayout(new BorderLayout());
        setTitle("JS PAINT");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 720));

        JPanel coordsPanel = new JPanel();
        coordsPanel.setBackground(Color.LIGHT_GRAY);
        coordsPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        coordsPanel.add(coords);

        JMenuBar menuBar = new JMenuBar();
        JMenu files = new JMenu("File");
        JMenu insert = new JMenu("Insert");
        JMenu filters = new JMenu("Filters");
        JMenu tools = new JMenu("Tools");
        JMenu transform = new JMenu("Transform");

        menuBar.add(files);
        menuBar.add(insert);
        menuBar.add(filters);
        menuBar.add(tools);
        menuBar.add(transform);

        files.add(newFile);
        files.add(openFile);
        files.add(saveFile);
        files.add(exit);

        insert.add(line);
        insert.add(rectangle);
        insert.add(circle);
        insert.add(point);

        filters.add(add);
        filters.add(subtract);
        filters.add(multiply);
        filters.add(divide);
        filters.add(darken);
        filters.add(grayscale);
        filters.add(equalize);
        filters.add(sharpen);
        filters.add(blur);
        filters.add(median);
        filters.add(sobel);
        filters.add(filter);

        tools.add(histograms);
        tools.add(binarization);
        tools.add(morphology);
        tools.add(analyzer);

        transform.add(tMove);
        transform.add(tRotate);
        transform.add(tScale);

        add(menuBar, BorderLayout.NORTH);
        add(paintPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.EAST);
        add(coordsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
