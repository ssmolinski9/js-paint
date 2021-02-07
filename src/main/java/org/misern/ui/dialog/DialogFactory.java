package org.misern.ui.dialog;

import javax.swing.JFrame;
import org.misern.shapes.utils.ShapeType;

/**
 * Factory of custom dialogs based on shapes type
 * @version 0.1
 * @since 0.1
 * @see CustomDialog
 * @see ShapeType
 */
public class DialogFactory {
    public static CustomDialog createDialog(JFrame owner, String type) {
        if (type == null) {
            return null;
        }

        CustomDialog customDialog = new CustomDialog(owner, true);
        switch (type) {
            case "Line":
                customDialog.setTitle("Create " + ShapeType.LINE);
                return customDialog;
            case "Rectangle":
                customDialog.setTitle("Create " + ShapeType.RECTANGLE);
                return customDialog;
            case "Circle":
                customDialog.setTitle("Create " + ShapeType.CIRCLE);
                return customDialog;
            default:
                return null;
        }
    }
}
