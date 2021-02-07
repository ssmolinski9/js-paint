package org.misern.ui.colors;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;
import lombok.Getter;
import org.misern.ui.PaintPanel;

/**
 * Panel with color values and color preview parts
 * @version 0.3
 * @since 0.3
 * @see javax.swing.JSplitPane
 */
@Getter
public class RGBChooser extends JSplitPane {

    private final JSpinner redValue = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
    private final JSpinner greenValue = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
    private final JSpinner blueValue = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));

    private final JSlider redSlider = new JSlider(0, 255, 0);
    private final JSlider greenSlider = new JSlider(0, 255, 0);
    private final JSlider blueSlider = new JSlider(0, 255, 0);

    private final JPanel colorPanel = new JPanel();

    private final PaintPanel paintPanel;

    /**
     * RGB Chooser element with sliders for every color value
     * @param panel paint panel to set actual color in
     */
    public RGBChooser(PaintPanel panel) {
        super();

        this.paintPanel = panel;
        colorPanel.setBackground(panel.getSelectedColor());

        JPanel toolsPanel = new JPanel(new BorderLayout());
        Box mainBox = Box.createVerticalBox();

        Box redSliderBox = createRedSliderSection();
        Box greenSliderBox = createGreenSliderSection();
        Box blueSliderBox = createBlueSliderSection();

        mainBox.add(redSliderBox);
        mainBox.add(Box.createVerticalStrut(1));
        mainBox.add(greenSliderBox);
        mainBox.add(Box.createVerticalStrut(1));
        mainBox.add(blueSliderBox);
        mainBox.add(Box.createVerticalStrut(1));

        toolsPanel.add(mainBox);

        setLeftComponent(colorPanel);
        setRightComponent(toolsPanel);

        setDividerLocation(150);
        setEnabled(false);
    }

    private Box createRedSliderSection() {
        Box sliderBox = Box.createHorizontalBox();

        redValue.setValue(colorPanel.getBackground().getRed());
        redSlider.setValue(colorPanel.getBackground().getRed());

        redSlider.getModel().addChangeListener(changeEvent -> {
            redValue.setValue(redSlider.getValue());

            Color generated = new Color(redSlider.getValue(), colorPanel.getBackground().getGreen(), colorPanel.getBackground().getBlue());
            colorPanel.setBackground(generated);
            paintPanel.setSelectedColor(generated);
        });

        redValue.getModel().addChangeListener(changeEvent -> {
            redSlider.setValue((Integer) redValue.getValue());

            Color generated = new Color(redSlider.getValue(), colorPanel.getBackground().getGreen(), colorPanel.getBackground().getBlue());
            colorPanel.setBackground(generated);
            paintPanel.setSelectedColor(generated);
        });

        sliderBox.add(redSlider);
        sliderBox.add(Box.createGlue());
        sliderBox.add(redValue);
        sliderBox.add(Box.createGlue());

        return sliderBox;
    }

    private Box createGreenSliderSection() {
        Box sliderBox = Box.createHorizontalBox();

        greenValue.setValue(colorPanel.getBackground().getGreen());
        greenSlider.setValue(colorPanel.getBackground().getGreen());

        greenSlider.getModel().addChangeListener(changeEvent -> {
            greenValue.setValue(greenSlider.getValue());

            Color generated = new Color(colorPanel.getBackground().getRed(), greenSlider.getValue(), colorPanel.getBackground().getBlue());
            colorPanel.setBackground(generated);
            paintPanel.setSelectedColor(generated);
        });

        greenValue.getModel().addChangeListener(changeEvent -> {
            greenSlider.setValue((Integer) greenValue.getValue());

            Color generated = new Color(colorPanel.getBackground().getRed(), greenSlider.getValue(), colorPanel.getBackground().getBlue());
            colorPanel.setBackground(generated);
            paintPanel.setSelectedColor(generated);
        });

        sliderBox.add(greenSlider);
        sliderBox.add(Box.createGlue());
        sliderBox.add(greenValue);
        sliderBox.add(Box.createGlue());

        return sliderBox;
    }

    private Box createBlueSliderSection() {
        Box sliderBox = Box.createHorizontalBox();

        blueValue.setValue(colorPanel.getBackground().getBlue());
        blueSlider.setValue(colorPanel.getBackground().getBlue());

        blueSlider.getModel().addChangeListener(changeEvent -> {
            blueValue.setValue(blueSlider.getValue());

            Color generated = new Color(colorPanel.getBackground().getRed(), colorPanel.getBackground().getGreen(), blueSlider.getValue());
            colorPanel.setBackground(generated);
            paintPanel.setSelectedColor(generated);
        });

        blueValue.getModel().addChangeListener(changeEvent -> {
            blueSlider.setValue((Integer) blueValue.getValue());

            Color generated = new Color(colorPanel.getBackground().getRed(), colorPanel.getBackground().getGreen(), blueSlider.getValue());
            colorPanel.setBackground(generated);
            paintPanel.setSelectedColor(generated);
        });

        sliderBox.add(blueSlider);
        sliderBox.add(Box.createGlue());
        sliderBox.add(blueValue);
        sliderBox.add(Box.createGlue());

        return sliderBox;
    }

    /**
     * Updates RGB color value based on CMYK values
     * @param cyan 0-100 cyan value as percentage of this color
     * @param magenta 0-100 magenta value as percentage of this color
     * @param yellow 0-100 yellow value as percentage of this color
     * @param key 0-100 black value as percentage of this color
     */
    public void updateRGB(int cyan, int magenta, int yellow, int key) {
        double calculatedRed = (1.0 - Math.min(1.0, (cyan / 100.0 * (1.0 - key / 100.0)) + key / 100.0)) * 255;
        double calculatedGreen = (1.0 - Math.min(1.0, (magenta / 100.0 * (1.0 - key / 100.0)) + key / 100.0)) * 255;
        double calculatedBlue = (1.0 - Math.min(1.0, (yellow / 100.0 * (1.0 - key / 100.0)) + key / 100.0)) * 255;

        redValue.setValue((int) calculatedRed);
        redSlider.setValue((int) calculatedRed);

        greenValue.setValue((int) calculatedGreen);
        greenSlider.setValue((int) calculatedGreen);

        blueValue.setValue((int) calculatedBlue);
        blueSlider.setValue((int) calculatedBlue);

        colorPanel.setBackground(new Color((int) calculatedRed, (int) calculatedGreen, (int) calculatedBlue));
    }
}
