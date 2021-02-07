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
 * @see JSplitPane
 */
@Getter
public class CMYKChooser extends JSplitPane {

    private final JSpinner cyanValue = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
    private final JSpinner magentaValue = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
    private final JSpinner yellowValue = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
    private final JSpinner keyValue = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

    private final JSlider cyanSlider = new JSlider(0, 100, 0);
    private final JSlider magentaSlider = new JSlider(0, 100, 0);
    private final JSlider yellowSlider = new JSlider(0, 100, 0);
    private final JSlider keySlider = new JSlider(0, 100, 0);

    private final JPanel colorPanel = new JPanel();

    private final PaintPanel paintPanel;

    /**
     * CMYK Chooser element with sliders for every color value
     * @param panel paint panel to set actual color in
     */
    public CMYKChooser(PaintPanel panel) {
        super();

        this.paintPanel = panel;
        colorPanel.setBackground(panel.getSelectedColor());

        JPanel toolsPanel = new JPanel(new BorderLayout());
        Box mainBox = Box.createVerticalBox();

        Box keySliderBox = createKeySliderSection();
        Box cyanSliderBox = createCyanSliderSection();
        Box magentaSliderBox = createMagentaSliderSection();
        Box yellowSliderBox = createYellowSliderSection();

        mainBox.add(cyanSliderBox);
        mainBox.add(Box.createVerticalStrut(1));
        mainBox.add(magentaSliderBox);
        mainBox.add(Box.createVerticalStrut(1));
        mainBox.add(yellowSliderBox);
        mainBox.add(Box.createVerticalStrut(1));
        mainBox.add(keySliderBox);
        mainBox.add(Box.createVerticalStrut(1));

        toolsPanel.add(mainBox);

        setLeftComponent(colorPanel);
        setRightComponent(toolsPanel);

        setDividerLocation(150);
        setEnabled(false);
    }

    private Box createCyanSliderSection() {
        Box sliderBox = Box.createHorizontalBox();
        updateCyanValue(colorPanel.getBackground().getRed());

        cyanSlider.getModel().addChangeListener(changeEvent -> {
            cyanValue.setValue(cyanSlider.getValue());
            updateColors();
        });

        cyanValue.getModel().addChangeListener(changeEvent -> {
            cyanSlider.setValue((Integer) cyanValue.getValue());
            updateColors();
        });

        sliderBox.add(cyanSlider);
        sliderBox.add(Box.createGlue());
        sliderBox.add(cyanValue);
        sliderBox.add(Box.createGlue());

        return sliderBox;
    }

    private Box createMagentaSliderSection() {
        Box sliderBox = Box.createHorizontalBox();
        updateMagentaValue(colorPanel.getBackground().getGreen());

        magentaSlider.getModel().addChangeListener(changeEvent -> {
            magentaValue.setValue(magentaSlider.getValue());
            updateColors();
        });

        magentaValue.getModel().addChangeListener(changeEvent -> {
            magentaSlider.setValue((Integer) magentaValue.getValue());
            updateColors();
        });

        sliderBox.add(magentaSlider);
        sliderBox.add(Box.createGlue());
        sliderBox.add(magentaValue);
        sliderBox.add(Box.createGlue());

        return sliderBox;
    }

    private Box createYellowSliderSection() {
        Box sliderBox = Box.createHorizontalBox();
        updateYellowValue(colorPanel.getBackground().getBlue());

        yellowSlider.getModel().addChangeListener(changeEvent -> {
            yellowValue.setValue(yellowSlider.getValue());
            updateColors();
        });

        yellowValue.getModel().addChangeListener(changeEvent -> {
            yellowSlider.setValue((Integer) yellowValue.getValue());
            updateColors();
        });

        sliderBox.add(yellowSlider);
        sliderBox.add(Box.createGlue());
        sliderBox.add(yellowValue);
        sliderBox.add(Box.createGlue());

        return sliderBox;
    }

    private Box createKeySliderSection() {
        Box sliderBox = Box.createHorizontalBox();
        updateKeyValue(colorPanel.getBackground().getRed(), colorPanel.getBackground().getGreen(), colorPanel.getBackground().getBlue());

        keySlider.getModel().addChangeListener(changeEvent -> {
            keyValue.setValue(keySlider.getValue());
            updateColors();
        });

        keyValue.getModel().addChangeListener(changeEvent -> {
            keySlider.setValue((Integer) keyValue.getValue());
            updateColors();
        });

        sliderBox.add(keySlider);
        sliderBox.add(Box.createGlue());
        sliderBox.add(keyValue);
        sliderBox.add(Box.createGlue());

        return sliderBox;
    }

    private void updateColors() {
        double calculatedRed = (1.0 - Math.min(1.0, (cyanSlider.getValue()/100.0 * (1.0 - keySlider.getValue()/100.0)) + keySlider.getValue()/100.0)) * 255;
        double calculatedGreen = (1.0 - Math.min(1.0, (magentaSlider.getValue()/100.0 * (1.0 - keySlider.getValue()/100.0)) + keySlider.getValue()/100.0)) * 255;
        double calculatedBlue = (1.0 - Math.min(1.0, (yellowSlider.getValue()/100.0 * (1.0 - keySlider.getValue()/100.0)) + keySlider.getValue()/100.0)) * 255;

        colorPanel.setBackground(new Color((int)calculatedRed, (int)calculatedGreen, (int)calculatedBlue));
        paintPanel.setSelectedColor(new Color((int)calculatedRed, (int)calculatedGreen, (int)calculatedBlue));
    }

    private void updateCyanValue(int red) {
        double black = keySlider.getValue() / 100.0;
        double cyan = (black != 1.0) ? (1.0 - red / 255.0 - black) / (1.0 - black) : 0.0;

        cyanValue.setValue((int) Math.max(0, cyan*100));
        cyanSlider.setValue((int) Math.max(0, cyan*100));
        updateColors();
    }

    private void updateMagentaValue(int green) {
        double black = keySlider.getValue() / 100.0;
        double magenta = (black != 1.0) ? (1.0 - green / 255.0 - black) / (1.0 - black) : 0.0;

        magentaValue.setValue((int) Math.max(0, magenta*100));
        magentaSlider.setValue((int) Math.max(0, magenta*100));
        updateColors();
    }

    private void updateYellowValue(int blue) {
        double black = keySlider.getValue() / 100.0;
        double yellow = (black != 1.0) ? (1.0 - blue / 255.0 - black) / (1.0 - black) : 0.0;

        yellowValue.setValue((int) Math.max(0, yellow*100));
        yellowSlider.setValue((int) Math.max(0, yellow*100));
        updateColors();
    }

    /**
     * Updates CMYK values based on RGB
     * @param red 0-255 red color value
     * @param green 0-255 green color value
     * @param blue 0-255 blue color value
     */
    public void updateKeyValue(int red, int green, int blue) {
        double black = Math.min(Math.min(1.0 - red / 255.0, 1.0 - green / 255.0), 1.0 - blue / 255.0) * 100;

        keyValue.setValue((int) Math.max(0, black));
        keySlider.setValue((int) Math.max(0, black));

        updateCyanValue(red);
        updateMagentaValue(green);
        updateYellowValue(blue);

        updateColors();
    }
}
