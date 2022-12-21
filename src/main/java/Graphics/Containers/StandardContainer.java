package Graphics.Containers;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interfaces.ContainerInterface;
import Utilities.GBC;

public class StandardContainer extends JPanel implements ContainerInterface {
    private final JLabel header;
    private final JLabel description;
    private final JComponent content;

    public StandardContainer(Color bgColor, JLabel header, JLabel description, JComponent content) {
        super(new GridBagLayout());
        super.setBackground(bgColor);
        super.setOpaque(true);
        super.setVisible(true);
        this.header = header;
        this.description = description;
        this.content = content;

        layoutView();
    }

    /**
     * Initializes the container's layout, called from the constructor.
     */
    @Override
    public void layoutView() {
        GridBagConstraints gbc = new GridBagConstraints();
        int i = 0;

        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // If the header object is set, go ahead and lay it out first.
        if (this.header != null) {
            super.add(this.header, GBC.setGBC(gbc, 0, i++, 1.0));
        }

        // If the description is not null, place it next in the layout order.
        if (this.description != null) {
            super.add(this.description, GBC.setGBC(gbc, 0, i++, 1.0));
        }

        // Finally, place the main content below.
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        super.add(content, GBC.setGBC(gbc, 0, i++, 1.0));

        // And add an anchor panel to push the layout north.
        super.add(GBC.anchorPanel(), GBC.setToAnchorBottom(gbc, 0, i, 2));
    }

    @Override
    public JComponent getContent() {
        return this.content;
    }
}
