package Graphics.Text;

import Utilities.Styler;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Enforces style-constraints for a JLabel.
 * @deprecated replaced with {@code TextFactory}
 */
public class TitleBox extends JPanel {
    private final Color THEME_COLOR = Styler.THEME_COLOR;
    private final Font FONT = new Font("Arial", Font.BOLD, 18);
    private final int WIDTH = 350;
    private final int HEIGHT = 24;

    public TitleBox(String titleText, Color bgColor) {
        super(new GridBagLayout());

        JLabel title = new JLabel(titleText);
        title.setOpaque(true);
        title.setVisible(true);
        title.setBackground(bgColor);
        title.setForeground(Styler.CONTAINER_BACKGROUND);
        title.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 0));
        title.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        title.setFont(this.FONT);
        super.add(title, setGBC(0, 0.0, 0.5));

        JLabel blank = new JLabel();
        blank.setOpaque(true);
        blank.setVisible(true);
        blank.setBackground(this.THEME_COLOR);
        super.add(blank, setGBC(1, 1.0, 0.5));
    }

    private GridBagConstraints setGBC(int x, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        return gbc;
    }
}
