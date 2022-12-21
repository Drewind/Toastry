package Graphics.Factories;

import Graphics.RoundedButton;
import Graphics.Text.TextButton;
import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class ButtonFactory {
    private static final Color defaultBgColor = Styler.CONTAINER_BACKGROUND;
    private static final Color fgColor = Styler.TEXT_COLOR;

    /**
     * Returns a button with some standard styling but is flexible enough for
     * most requirements. Formats the text color, sets background visibility to false,
     * and sets the border to empty.
     *
     * Accepts a string argument for the button text and a font object.
     * @param text {@code String} text to be displayed
     * @param font {@code Font} font to be used
     * @return {@code JButton}
     */
    public static JButton buildStandardButton(String text, Color bgColor, Font font) {
        return new JButton(text) {{
            setBackground(bgColor != null ? bgColor : defaultBgColor);
            setForeground(fgColor);
            setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            setFont(font);
            setOpaque(bgColor != null);
        }};
    }

    public static JButton buildRoundButton(String text, Color bgColor, Font font) {
        return new RoundedButton(text) {{
            setBackground(bgColor);
            setFont(font);
        }};
    }

    /**
     * Returns a submit button with standard styling. Designed for forms.
     * @param text {@code String} text to be displayed
     * @return {@code JButton}
     */
    public static JButton buildSubmitButton(String text) {
        return new TextButton(
                defaultBgColor,
                fgColor,
                BorderFactory.createMatteBorder(2, 2, 2, 2, Styler.CONTAINER_BACKGROUND.darker()),
                text,
                Styler.REGULAR_FONT.deriveFont(Styler.REGULAR_FONT.getSize() + 4f),
                true
        );
    }

    public static JButton buildImageIcon(final ImageIcon icon) {
        return new JButton(icon) {{
            setBackground(defaultBgColor);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        }};
    }
}
