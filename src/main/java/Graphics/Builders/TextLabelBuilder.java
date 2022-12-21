package Graphics.Builders;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import Graphics.Text.TextLabel;
import Utilities.Styler;

/**
 * Constructs a new text label to be used in the program.
 * Assigns default values unless otherwise specified.
 */
public class TextLabelBuilder {
    private Color _bgColor; // Defaults to the standard container bg.
    private Color _fgColor = Styler.TEXT_COLOR; // Optional header with default header properties.
    private String _text; // Optional description to describe this container.
    private Font _font = Styler.REGULAR_FONT;
    private String _name;
    private boolean _bgVisible = false;
    private boolean _isVisible = true;
    private int _horizontalAlignment = JLabel.LEFT;
    private boolean _wrapText = true;

    public TextLabelBuilder() {}

    public TextLabel buildLabel() {
        return new TextLabel(
                _name,
                _bgColor,
                _fgColor,
                _text,
                _font,
                _bgVisible,
                _isVisible,
                _horizontalAlignment
        );
    }

    public JTextArea buildTextArea() {
        return new JTextArea() {{
            setBackground(_bgColor);
            setForeground(_fgColor);
            setText(_text);
            setFont(_font);
            setOpaque(_bgVisible);
            setLineWrap(_wrapText);
            setEnabled(false);
            setDragEnabled(false);
        }};
    }

    /**
     * Assigns a background color to the label.
     * By default, the background will be transparent.
     * @param _bgColor Color
     */
    public TextLabelBuilder bgColor(Color _bgColor) {
        this._bgColor = _bgColor;
        this._bgVisible = true;
        return this;
    }

    /**
     * Assigns the text (foreground) color.
     * Defaults to the universal text color.
     * @param _fgColor Color.
     */
    public TextLabelBuilder fgColor(Color _fgColor) {
        this._fgColor = _fgColor;
        return this;
    }

    /**
     * Assigns the text of the label.
     * @param _text text to be inserted into the header object.
     */
    public TextLabelBuilder text(String _text) {
        this._text = _text;
        return this;
    }

    /**
     * Assigns the font size of this label.
     * When assigned, it will reconstruct the font object
     * with the new font size, since we can't update the size of the font.
     * @param _fontSize Integer
     */
    public TextLabelBuilder fontSize(int _fontSize) {
        this._font = new Font(_font.getFamily(), _font.getStyle(), _fontSize);
        return this;
    }

    public TextLabelBuilder raiseFontSize(int _fontSize) {
        this._font = new Font(_font.getFamily(), _font.getStyle(), _font.getSize() + _fontSize);
        return this;
    }

    /**
     * Assigns the font style of this label to BOLD.
     * When assigned, it will reconstruct the font object
     * with the new font style, since we can't update the size of the font.
     */
    public TextLabelBuilder boldify() {
        this._font = new Font(_font.getFamily(), Font.BOLD, _font.getSize());
        return this;
    }

    /**
     * By default, the builder will build a {@code TextLabel} and call {@code setVisible(true)},
     * showing the element on screen. This method will instead hide the element from view by calling
     * {@code setVisible(false)}. Note this is different from setting background visibility.
     */
    public TextLabelBuilder hide() {
        this._isVisible = false;
        return this;
    }

    public TextLabelBuilder name(String _name) {
        this._name = _name;
        return this;
    }

    public TextLabelBuilder resetFont() {
        this._font = Styler.REGULAR_FONT;
        return this;
    }

    /**
     * Assigns the horizontal alignment of this label.
     * When assigned, it will reconstruct the font object
     * with the new font size, since we can't update the size of the font.
     * @param _horizontalAlignment Integer
     */
    public TextLabelBuilder horizontal(int _horizontalAlignment) {
        this._horizontalAlignment = _horizontalAlignment;
        return this;
    }

    /**
     * Assigns a new font to the label.
     * @param _font Font object
     */
    public TextLabelBuilder font(Font _font) {
        this._font = _font;
        return this;
    }

    /**
     * Will wrap words when building a JTextArea.
     * Defaults to true.
     * @param _wrapText Font object
     */
    public TextLabelBuilder wrapByWord(boolean _wrapText) {
        this._wrapText = _wrapText;
        return this;
    }
}