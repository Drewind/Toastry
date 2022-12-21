package Graphics.Factories;

import Graphics.Builders.TextLabelBuilder;
import Utilities.Styler;

import javax.swing.*;
import java.awt.Font;

public class TextFactory {
    /**
     * Returns a {@code JLabel} with some standard styling but is flexible enough for
     * most requirements. Formats the text color, sets background visibility to false,
     * and sets the border to empty.
     *
     * Accepts a string argument for the button text and a font object.
     * @param text {@code String} text to be displayed
     * @return {@code JLabel}
     */
    public static JLabel buildStandardHeader(String text) {
        return new JLabel() {{
            setForeground(Styler.TEXT_COLOR);
            setText(text);
            setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
            setFont(Styler.REGULAR_FONT.deriveFont(Font.BOLD, Styler.REGULAR_FONT.getSize() + 4f));
        }};
    }

    /**
     * Returns an {@code JLabel} built with the default configurations and style. Under the hood,
     * it will construct a TextLabelBuilder and build the label.
     * @param text {@code String} desired text
     * @param text {@code String} name of label
     * @return {@code JLabel}
     */
    public static JLabel buildLabel(final String text) {
        TextLabelBuilder textBuilder = new TextLabelBuilder();
        return textBuilder.text(text).buildLabel();
    }

    public static JTextArea buildTextArea(final String text) {
        TextLabelBuilder textBuilder = new TextLabelBuilder();
        return textBuilder.text(text).buildTextArea();
    }

    /**
     * Returns an {@code JLabel} formatted to display a list of error messages in forms. Recommended to
     * create a JLabel above this message and format it with {@code buildFormErrorHeader()}, which
     * will keep the form unified.
     * @return {@code JLabel}
     */
    public static JLabel buildFormErrorMessage() {
        return new JLabel() {{
            setForeground(Styler.DANGER_COLOR.brighter());
            setFont(new Font("Arial", Font.PLAIN, 16));
            setOpaque(false);
        }};
    }

    /**
     * Returns an {@code JLabel} formatted with the standard error message header suitable for input forms.
     * Right below this error header would be the list of error messages.
     * @return {@code JLabel}
     */
    public static JLabel buildFormErrorHeader() {
        return new JLabel() {{
            setText("ERRORS");
            setForeground(Styler.DANGER_COLOR);
            setFont(new Font("Arial Bold", Font.BOLD, 20));
            setVisible(false);
            setOpaque(false);
        }};
    }
}
