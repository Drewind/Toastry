package Views.Home.Partials;

import Graphics.Containers.OverviewContainer;
import Utilities.GBC;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

/**
 * Simple partial to render the containers as defined by the layout.
 */
public class DashboardSummary extends JPanel {
    public DashboardSummary(final ArrayList<OverviewContainer> containers) {
        super(new GridBagLayout());
        super.setVisible(true);
        super.setOpaque(false);

        init(containers);
    }

    private void init(ArrayList<OverviewContainer> containers) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 20, 0, 20);
        int i = 0;

        for (OverviewContainer container : containers) {
            super.add(container, GBC.setGBC(gbc, i++, 0, 1.0));
        }
    }
}
