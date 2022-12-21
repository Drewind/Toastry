package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import Utilities.Styler;

/**
 * Used by the Navbar to create clickable tabs.
 */
public class TabButton extends JButton {
    private final Font FONT = new Font("Arial", Font.BOLD, 18);
    private Color activeBackground = Styler.APP_BG_COLOR;
    private final Color inactiveBackground = Styler.DARK_SHADE2_COLOR;
    private final Color activeColor = Styler.THEME_COLOR;
    private final Color inactiveColor = new Color(247, 247, 247);

    private boolean isActive = false;

    /**
     * TabButton
     * Creates default button with template colors.
     * @param text String
     */
    public TabButton(String text) {
        super(text);
        this.setDefaults();
    }

    /**
     * TabButton
     * Creates new tab button based on known dimensions. Defaults to inactive status.
     * @param text String
     * @param size Dimension of tab
     */
    public TabButton(String text, Dimension size) {
        super(text);
        this.setDefaults();
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
    }

    /**
     * TabButton
     * Creates new tab button based on known dimensions. Defaults to inactive status.
     * @param text String
     * @param size Dimension of tab
     */
    public TabButton(String text, Dimension size, Color bgColor) {
        super(text);
        this.setDefaults();
        this.activeBackground = bgColor;
        this.setOpaque(true);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
    }

    private void setDefaults() {
        this.setBackground(this.inactiveBackground);
        this.setForeground(this.inactiveColor);
        this.setFont(this.FONT);
        this.setVisible(true);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Styler.DARK_SHADE2_COLOR));
        setAsInactive();
    }

    public void setAsActive() {
        this.isActive = true;
        this.setBackground(this.activeBackground);
        this.setForeground(this.activeColor);
        this.setBorderPainted(true);
    }

    public void setAsInactive() {
        this.isActive = false;
        this.setBackground(this.inactiveBackground);
        this.setForeground(this.inactiveColor);
        this.setBorderPainted(false);
    }

    public boolean isActive() {
        return this.isActive;
    }
}