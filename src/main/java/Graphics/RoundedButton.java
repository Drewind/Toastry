package Graphics;

import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Used by the Navbar to create clickable tabs.
 * Original rounded borders can be found in <a href="https://github.com/DJ-Raven/java-jpanel-round-border">this</a>
 * GitHub repo.
 */
public class RoundedButton extends JButton {
    private final Font FONT = new Font("Arial", Font.BOLD, 16);
    private final Color activeBackground = Styler.APP_BG_COLOR;
    private final Color inactiveBackground = Styler.DANGER_COLOR;
    private final Color activeColor = Styler.THEME_COLOR;
    private final Color inactiveColor = new Color(247, 247, 247);
    private final int roundRadius = 20;

    private boolean isActive = false;

    /**
     * TabButton
     * Creates default button with template colors.
     * @param text String
     */
    public RoundedButton(String text) {
        super(text);
        this.setDefaults();
    }

    private void setDefaults() {
        this.setBackground(this.inactiveBackground);
        this.setForeground(this.inactiveColor);
        this.setFont(this.FONT);
        this.setVisible(true);
        this.setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
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

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundRadius);
        int roundY = Math.min(height, roundRadius);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());

        Area area = new Area(createRoundTopLeft(width, height, roundX, roundY));
        area.intersect(new Area(createRoundTopRight(width, height, roundX, roundY)));
        area.intersect(new Area(createRoundBottomLeft(width, height, roundX, roundY)));
        area.intersect(new Area(createRoundBottomRight(width, height, roundX, roundY)));

        g2.fill(area);
        g2.dispose();
        super.paintComponent(g);
    }

    private Shape createRoundTopLeft(int width, int height, int roundX, int roundY) {
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundTopRight(int width, int height, int roundX, int roundY) {
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomLeft(int width, int height, int roundX, int roundY) {
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomRight(int width, int height, int roundX, int roundY) {
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }
}