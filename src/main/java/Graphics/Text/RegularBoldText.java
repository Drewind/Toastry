package Graphics.Text;

import Utilities.Styler;

import javax.swing.JLabel;

/**
 * Enforces style-constraints for a JLabel.
 * @deprecated replaced with {@code TextFactory}
 */
public class RegularBoldText extends JLabel {
    public RegularBoldText(String message) {
        super(message);
        this.setDefaults();
    }

    public RegularBoldText(String text, int alignment) {
        super(text);
        this.setDefaults();
        this.setHorizontalAlignment(alignment);
    }

    public void setDefaults() {
        this.setOpaque(false);
        this.setFont(Styler.REGULAR_FONT);
        this.setForeground(Styler.THEME_COLOR);
    }

    public void setText(String text) {
        super.setText(text);
    }
}