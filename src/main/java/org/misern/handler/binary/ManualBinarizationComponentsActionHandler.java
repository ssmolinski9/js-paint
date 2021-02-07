package org.misern.handler.binary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.misern.ui.BinarizationFrame;

public class ManualBinarizationComponentsActionHandler implements ActionListener {

    private final BinarizationFrame binarizationFrame;

    public ManualBinarizationComponentsActionHandler(BinarizationFrame binarizationFrame) {
        this.binarizationFrame = binarizationFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.binarizationFrame.getBinarizationLevel().setEnabled(true);
        this.binarizationFrame.getPercentageLevel().setEnabled(false);
        this.binarizationFrame.getConfirmManuallyBinarization().setEnabled(true);
        this.binarizationFrame.getConfirmPercentageBinarization().setEnabled(false);
    }
}
