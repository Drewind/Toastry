package Utilities;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 * GridBagConstraints toolbox for quickly formatting views. Somewhat in use.
 */
public class GBC {
    private static final int LAYOUT_MARGIN_X = 22;
    private static final int LAYOUT_MARGIN_Y = 10;

    // NEW
    public static GridBagConstraints standardGBC() {
        return new GridBagConstraints() {{
            anchor = GridBagConstraints.NORTH;
            fill = GridBagConstraints.BOTH;
            gridheight = 1;
            gridwidth = 1;
            weightx = 1.0;
            weighty = 0.0;
        }};
    }

    // NEW
    public static GridBagConstraints setVertical(final int y) {
        return new GridBagConstraints() {{
            anchor = GridBagConstraints.NORTH;
            fill = GridBagConstraints.BOTH;
            gridheight = 1;
            gridwidth = 1;
            weightx = 1.0;
            weighty = 0.0;
            gridy = y;
        }};
    }

    // NEW
    public static GridBagConstraints setHorizontal(final int x) {
        return new GridBagConstraints() {{
            anchor = GridBagConstraints.NORTH;
            fill = GridBagConstraints.BOTH;
            gridheight = 1;
            gridwidth = 1;
            weightx = 1.0;
            weighty = 0.0;
            gridx = x;
        }};
    }

    // NEW
    public static GridBagConstraints setGBC(final GridBagConstraints gbc, final int x, final int y) {
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    // NEW
    public static GridBagConstraints horizontal(final int x) {
        return new GridBagConstraints() {{
            anchor = GridBagConstraints.NORTH;
            fill = GridBagConstraints.BOTH;
            insets = new Insets(16, 20, 0, 20);
            gridheight = 1;
            gridwidth = 1;
            weightx = 1.0;
            weighty = 0.0;
            gridx = x;
        }};
    }

    /**
     * Used to set the layout constraints for an element.
     * Defaults to weighty = 0.0 with {@code gridwidth} and {@code gridheight} = 1.
     * @param gbc GridBagConstraints
     * @param x int
     * @param y int
     * @param weight double, weightx
     * @return GridBagConstraints
     */
    public static GridBagConstraints setGBC(final GridBagConstraints gbc, final int x, final int y, final double weight) {
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = weight;
        gbc.weighty = 0.0;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    public static GridBagConstraints setGBC(final GridBagConstraints gbc, final int x, final int y, final double xweight, final double yweight) {
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = xweight;
        gbc.weighty = yweight;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    /**
     * Retrieves the application's main layout margins.
     * @return Insets
     */
    public static Insets getDefaultLayoutMargins() {
        return new Insets(LAYOUT_MARGIN_Y, LAYOUT_MARGIN_X, LAYOUT_MARGIN_Y, LAYOUT_MARGIN_X);
    }

    /**
     * Used to anchor a component to the bottom of the layout via GBC.SOUTH constraints. Be sure to set gridwidth
     * to something larger than the rest of the layout elements (value of two should work most of the time).
     * @param gbc GridBagConstraints
     * @param x int
     * @param y int
     * @param gridwidth int
     * @return GridBagConstraints
     */
    public static GridBagConstraints setToAnchorBottom(final GridBagConstraints gbc, final int x, final int y, final int gridwidth) {
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridheight = 1;
        gbc.gridwidth = gridwidth;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    public static GridBagConstraints setToAnchorBottom(final int y) {
        return new GridBagConstraints() {{
            anchor = GridBagConstraints.SOUTH;
            fill = GridBagConstraints.BOTH;
            gridheight = 1;
            gridwidth = 2;
            weightx = 1.0;
            weighty = 1.0;
            gridy = y;
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
