package Graphics.Text;

import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.awt.Font;

/**
 * Enforces style-constraints for a JLabel.
 * @deprecated replaced with {@code TextFactory}
 */
public class RegularText extends JLabel {
    private final Font REGULAR_FONT = new Font("Arial", Font.PLAIN, 12);

    public RegularText(String text) {
        super(text);
        this.setDefaults();
    }

    private void setDefaults() {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        this.setForeground(Styler.THEME_COLOR);
        this.setFont(this.REGULAR_FONT);
    }
}