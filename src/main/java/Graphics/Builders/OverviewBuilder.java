package Graphics.Builders;

import java.awt.Color;

import javax.swing.JLabel;

import Graphics.Containers.OverviewContainer;
import Utilities.Styler;

/**
 * Container factory using the builder pattern.
 */
public class OverviewBuilder {
    private Color _containerColor = Styler.CONTAINER_BACKGROUND.brighter();    // Defaults to the standard container bg.
    private Color _borderColor = Styler.CONTAINER_BACKGROUND;                  // Defaults to a lighter shade of the app background.
    private Color _textHeaderColor = Styler.TEXT_COLOR;                        // Defaults to the standard text color.
    private Color _textDescColor = Styler.TEXT_COLOR;                          // Defaults to the standard text color.
    private JLabel _content;
    private String _description;
    private boolean _formatCurrency = true;
    
    public OverviewBuilder() {}

    public OverviewContainer buildContainer() {
        return new OverviewContainer(_containerColor, _borderColor, _textHeaderColor, _textDescColor, _content, _description, _formatCurrency);
    }

    /**
     * Sets the background color of this container.
     *
     * Defaults to the standard CONTAINER_BACKGROUND.brighter()
     * for a slightly lighter shade.
     */
    public OverviewBuilder bgColor(Color _containerColor) {
        this._containerColor = _containerColor;
        return this;
    }

    /**
     * Sets both of the text components to a unified text color.
     */
    public OverviewBuilder textColor(Color _textColor) {
        this._textHeaderColor = _textColor;
        this._textDescColor = _textColor;
        return this;
    }

    /**
     * Sets the header component of the container. This component is bold and lies on top.
     */
    public OverviewBuilder textHeaderColor(Color _textColor) {
        this._textHeaderColor = _textColor;
        return this;
    }

    /**
     * Sets the description text color. This lies on the bottom of the container.
     */
    public OverviewBuilder textDescColor(Color _textColor) {
        this._textDescColor = _textColor;
        return this;
    }

    /**
     * Sets the border of this container. Defaults to the container background.
     */
    public OverviewBuilder borderColor(Color _borderColor) {
        this._borderColor = _borderColor;
        return this;
    }

    /**
     * Sets the label object of the bold header container.
     */
    public OverviewBuilder content(JLabel _content) {
        this._content = _content;
        return this;
    }

    public OverviewBuilder contentText(String _text) {
        this._content = new JLabel(_text) {{
            setOpaque(false);
        }};
        return this;
    }

    /**
     * Sets the string text of the description of the container.
     */
    public OverviewBuilder description(String _text) {
        this._description = _text;
        return this;
    }

    public OverviewBuilder useCurrencyFormat(boolean _formatCurrency) {
        this._formatCurrency = _formatCurrency;
        return this;
    }
}