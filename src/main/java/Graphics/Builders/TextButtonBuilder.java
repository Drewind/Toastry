package Graphics.Builders;

import java.awt.Color;
import java.awt.Font;

import Graphics.Text.TextButton;
import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * Constructs a new clickable text button to be used in the program.
 * Assigns default values unless otherwise specified.
 */
public class TextButtonBuilder {
    private Color _bgColor; // Defaults to the standard container bg.
    private Color _fgColor = Styler.TEXT_COLOR; // Optional header with default header properties.
    private Border _border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
    private String _text; // Optional description to describe this container.
    private Font _font = Styler.REGULAR_FONT;
    private boolean _bgVisible = false;

    public TextButtonBuilder() {}

    public TextButton buildButton() {
        return new TextButton
                (
                        _bgColor,
                        _fgColor,
                        _border,
                        _text,
                        _font,
                        _bgVisible
                );
    }

    /**
     * Assigns a background color to the button.
     * By default, the background will be transparent.
     * @param _bgColor Color
     */
    public TextButtonBuilder bgColor(Color _bgColor) {
        this._bgColor = _bgColor;
        this._bgVisible = true;
        return this;
    }

    /**
     * Assigns the text (foreground) color.
     * Defaults to the universal text color.
     * @param _fgColor Color.
     */
    public TextButtonBuilder fgColor(Color _fgColor) {
        this._fgColor = _fgColor;
        return this;
    }

    public TextButtonBuilder border(Border _border) {
        this._border = _border;
        return this;
    }

    /**
     * Assigns the text of the button.
     * @param _text text to be inserted into the header object.
     */
    public TextButtonBuilder text(String _text) {
        this._text = _text;
        return this;
    }

    /**
     * Assigns a new font to the button.
     * @param _font Font object
     */
    public TextButtonBuilder font(Font _font) {
        this._font = _font;
        return this;
    }
}