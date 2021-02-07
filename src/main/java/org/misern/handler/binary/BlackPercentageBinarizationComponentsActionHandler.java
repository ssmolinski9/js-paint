package org.misern.handler.binary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.misern.ui.BinarizationFrame;

public class BlackPercentageBinarizationComponentsActionHandler implements ActionListener {

    private final BinarizationFrame binarizationFrame;

    public BlackPercentageBinarizationComponentsActionHandler(BinarizationFrame binarizationFrame) {
        this.binarizationFrame = binarizationFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.binarizationFrame.getBinarizationLevel().setEnabled(false);
        this.binarizationFrame.getPercentageLevel().setEnabled(true);
        this.binarizationFrame.getConfirmManuallyBinarization().setEnabled(false);
        this.binarizationFrame.getConfirmPercentageBinarization().setEnabled(true);
    }
}
