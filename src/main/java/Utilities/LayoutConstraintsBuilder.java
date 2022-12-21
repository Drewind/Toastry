package Utilities;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * A builder for {@code GridBagConstraints} to build complex views.
 */
public class LayoutConstraintsBuilder {
    private final int LAYOUT_MARGIN_X = 22;
    private final int LAYOUT_MARGIN_Y = 10;
    public int _gridx = 0;
    public int _gridy = 0;
    private double _scalex = 1.0;
    private double _scaley = 0;
    private int _padx = 0;
    private int _pady = 0;
    private int _anchor = GridBagConstraints.NORTH;
    private int _fill = GridBagConstraints.BOTH;
    private int _gridWidth = 1;
    private int _gridHeight = 1;
    private Insets _insets = new Insets(0, 0, 0, 0);

    /**
     * Formats constraints to be placed vertically, incrementing the current
     * {@code gridYValue}. Note this operation will apply the following values:
     * <ul>
     *     <li>{@code weighty} 0.0</li>
     * </ul>
     * @return {@code GridBagConstraints}
     */
    public GridBagConstraints layoutVertical() {
        return new GridBagConstraints() {{
            anchor = _anchor;
            fill = _fill;
            gridheight = 1;
            gridwidth = _gridWidth;
            weightx = _scalex;
            weighty = 0.0;
            gridy = ++_gridy;
            gridx = 0;
            ipadx = _padx;
            ipady = _pady;
            insets = _insets;
        }};
    }

    /**
     * Formats constraints to be placed horizontally, incrementing the current
     * {@code gridXValue}. Note this operation will apply the following values:
     * <ul>
     *     <li>{@code weightx} 0.0</li>
     * </ul>
     * @return {@code GridBagConstraints}
     */
    public GridBagConstraints layoutHorizontal() {
        return new GridBagConstraints() {{
            anchor = _anchor;
            fill = _fill;
            gridheight = 1;
            gridwidth = _gridWidth;
            weightx = 0.0;
            weighty = _scaley;
            gridy = 0;
            gridx = ++_gridx;
            ipadx = _padx;
            ipady = _pady;
            insets = _insets;
        }};
    }

    public GridBagConstraints layoutCustom(final int x, final int y) {
        return new GridBagConstraints() {{
            anchor = _anchor;
            fill = _fill;
            gridheight = _gridHeight;
            gridwidth = _gridWidth;
            weightx = _scalex;
            weighty = _scaley;
            gridy = y;
            gridx = x;
            ipadx = _padx;
            ipady = _pady;
            insets = _insets;
        }};
    }

    /**
     * Assigns {@code gridx} and {@code gridy} to zero, resetting the grid increments.
     */
    public LayoutConstraintsBuilder resetIncrements() {
        this._gridy = 0;
        this._gridx = 0;
        return this;
    }

    public LayoutConstraintsBuilder noMargins() {
        this._insets = new Insets(0, 0, 0, 0);
        return this;
    }

    public LayoutConstraintsBuilder noPadding() {
        this._padx = 0;
        this._pady = 0;
        return this;
    }

    public LayoutConstraintsBuilder width(final int _gridWidth) {
        this._gridWidth = _gridWidth;
        return this;
    }

    public LayoutConstraintsBuilder padX(final int _padx) {
        this._padx = _padx;
        return this;
    }

    public LayoutConstraintsBuilder padY(final int _pady) {
        this._pady = _pady;
        return this;
    }

    public LayoutConstraintsBuilder gridHeight(final int _gridHeight) {
        this._gridHeight = _gridHeight;
        return this;
    }

    public LayoutConstraintsBuilder gridWidth(final int _gridWidth) {
        this._gridWidth = _gridWidth;
        return this;
    }

    public LayoutConstraintsBuilder scalex(final double _scalex) {
        this._scalex = _scalex;
        return this;
    }

    public LayoutConstraintsBuilder scaley(final double _scaley) {
        this._scaley = _scaley;
        return this;
    }

    /**
     * Sets the default application margins to this builder.
     */
    public LayoutConstraintsBuilder appMargins() {
        this._insets = new Insets(LAYOUT_MARGIN_Y, LAYOUT_MARGIN_X, LAYOUT_MARGIN_Y, LAYOUT_MARGIN_X);
        return this;
    }

    /**
     * Sets the {@code insets} property of the builder.
     * @param _insets {@code Insets}
     */
    public LayoutConstraintsBuilder insets(final Insets _insets) {
        this._insets = _insets;
        return this;
    }

    /**
     * Sets the {@code anchor} property of the builder.
     * @param _alignment
     */
    public LayoutConstraintsBuilder anchor(final int _alignment) {
        this._anchor = _alignment;
        return this;
    }

    public LayoutConstraintsBuilder fill(final int _fill) {
        this._fill = _fill;
        return this;
    }

    public GridBagConstraints anchorToBottom() {
        return new GridBagConstraints() {{
            anchor = GridBagConstraints.SOUTH;
            fill = GridBagConstraints.BOTH;
            gridheight = 1;
            gridwidth = 2;
            weightx = 1.0;
            weighty = 1.0;
            gridy = ++_gridy;
        }};
    }

    public GridBagConstraints anchorTo(final int direction) {
        return new GridBagConstraints() {{
            anchor = direction;
            fill = GridBagConstraints.BOTH;
            gridheight = 1;
            gridwidth = 2;
            weightx = 1.0;
            weighty = 1.0;
            gridy = ++_gridy;
        }};
    }

    /**
     * Simple method to return a blank panel to anchor to the bottom of a {@code gridbag} layout.
     * @return {@code JPanel} a blank panel
     */
    public static JPanel anchorPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }
}
