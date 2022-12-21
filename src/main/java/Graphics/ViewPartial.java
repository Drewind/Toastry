package Graphics;

import Constants.ViewLayoutStyle;
import Utilities.LayoutConstraintsBuilder;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

/**
 * A standard view with a {@code GridBagLayout} layout manager and additional tooling for
 * building views. Extends a {@code JPanel}.
 * <hr>
 * <br><br>
 * <h3>
 * Style Defaults
 * <p>
 * These are the defaults set when constructing a view:
 * <ul>
 *     <li>Layout manager is set to {@code GridBagLayout}</li>
 *     <ul>
 *          <li>Anchor set to {@code GridBagConstraints.NORTH}</li>
 *          <li>Fill set to {@code GridBagConstraints.BOTH}</li>
 *          <li>Insets set to blank</li>
 *     </ul>
 *     <li>Opaque is set to true when using {@code ViewPartial(Color bgColor)} constructor. Otherwise, set to false</li>
 * </ul>
 *
 * <h3>
 * Available Tooling
 * <p>
 * A {@code LayoutConstraintsBuilder} is initialized and freely usable by subclasses. Essentially a stateful
 * {@code GridBagConstraints} builder to construct layout constraints as necessary. Quick tips:
 * <ul>
 *     <li>Use {@code layoutVertical} to stack elements vertically</li>
 *     <li>Force a view to be positioned north by creating a empty JPanel via {@code anchorPanel} then call
 *     {@code anchorToBottom} on it</li>
 *     <li>Apply the standard application margins by chaining {@code appMargins} on the call</li>
 *     <li>Enforce a set desired height on a view with {@code enforceViewHeight}</li>
 *     <li>Creating multiple sections that need not be stateful? Use {@code newRow}!</li>
 * </ul>
 *
 */
public class ViewPartial extends JPanel {
    protected LayoutConstraintsBuilder layout = new LayoutConstraintsBuilder();
    public ViewPartial() { super(new GridBagLayout()); setOpaque(false); }
    public ViewPartial(Color bgColor) { super(new GridBagLayout()); setBackground(bgColor); }

    /**
     * Used to enforce this view's height to a constant value.
     * @param height {@code int} desired height
     */
    protected void enforceViewHeight(final int height) {
        setMinimumSize(new Dimension(0, height));
        setPreferredSize(new Dimension(0, height));
        setMaximumSize(new Dimension(0, height));
    }

    protected void enforceWidth(final JComponent component, final int width) {
        component.setMinimumSize(new Dimension(width, 0));
        component.setPreferredSize(new Dimension(width, 0));
        component.setMaximumSize(new Dimension(width, 0));
    }

    protected void enforceSize(final JComponent component, final int width, final int height) {
        component.setMinimumSize(new Dimension(width, height));
        component.setPreferredSize(new Dimension(width, height));
        component.setMaximumSize(new Dimension(width, height));
    }

    /**
     * Used to format two components in either a horizontal or vertical layout in relation to each other.
     * Set {@code style} to {@code ViewLayoutStyle.VERTICAL} to achieve a layout like this: A|B.
     * @param component1 first {@code JComponent}
     * @param component2 second {@code JComponent}
     * @param style {@code ViewLayoutStyle} chosen layout style
     * @return {@code JPanel}
     */
    protected JPanel newRow(JComponent component1, JComponent component2, ViewLayoutStyle style) {
        return new JPanel(new BorderLayout()) {{
            setOpaque(false);
            add(component1, (style.equals(ViewLayoutStyle.HORIZONTAL) ? BorderLayout.NORTH : BorderLayout.WEST));
            add(component2, (style.equals(ViewLayoutStyle.HORIZONTAL) ? BorderLayout.CENTER : BorderLayout.EAST));
        }};
    }

    protected JPanel anchorPanel() {
        return new JPanel() {{
            setOpaque(false);
        }};
    }
}
