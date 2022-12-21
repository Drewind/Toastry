package Graphics.Controls;

import Graphics.UI.SpinnerEditor;
import Graphics.UI.SpinnerUI;

import javax.swing.BorderFactory;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;

/**
 * A standard modern spinner control. Allows the user to click a button to either increment
 * or decrement a value. Accepts a {@code SpinnerNumberModel} to regulate the control.
 *
 * Since the control uses a separate {@code SpinnerUI} class, it is possible to modify this
 * class in the future to allow customization by the user.
 */
public class SpinnerInput extends JSpinner {
    public SpinnerInput(final SpinnerNumberModel spinnerModel) {
        super(spinnerModel);
        super.setBackground(new Color(61,61,61).brighter());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        setEditor(new SpinnerEditor(this));
        setUI(new SpinnerUI());
    }
}
