package Graphics.UI;

import Graphics.Controls.NumberInput;
import Graphics.InputDecimalFilter;
import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Font;

/**
 * A simple JPanel that acts as the user interface for displaying the
 * current value a SpinnerInput is currently at.
 *
 * Modifying a JSpinner in pure Swing/AWT is a little complicated, hence
 * why this class exists.
 */
public class SpinnerEditor extends JPanel implements ChangeListener {
    private final InputDecimalFilter docFilter = new InputDecimalFilter();
    private final JTextField label = new NumberInput(docFilter);

    public SpinnerEditor(JSpinner spinner) {
        setLayout(new BorderLayout());
        setBackground(Styler.APP_BG_COLOR.brighter());
        label.setForeground(Styler.TEXT_COLOR);
        label.setText(spinner.getValue().toString());
        label.setFont(Styler.REGULAR_FONT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(label, BorderLayout.CENTER);
        spinner.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        label.setText(spinner.getValue().toString());
    }
}
