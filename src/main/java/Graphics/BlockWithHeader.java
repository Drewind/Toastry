package Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * A standard view with a {@code GridBagLayout} layout manager and additional tooling for
 * building views. Extends a {@code JPanel}.
 */
public class BlockWithHeader extends JPanel {
    public BlockWithHeader(JLabel header) {
        super(new BorderLayout()); setOpaque(false);
        add(header, BorderLayout.NORTH);
    }
}
