package Views.Home.Partials;

import Graphics.Builders.ContainerBuilder;
import Interfaces.ViewPartialInterface;
import Models.ProductModel;
import Utilities.GBC;
import Utilities.Styler;
import Views.Home.StandardTable;
import Views.Home.TMTopSales;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * Displays products with the highest sales in the system.
 */
public class DashboardBestSellers extends JPanel implements ViewPartialInterface {
    private final TMTopSales tableModel;

    public DashboardBestSellers(final ProductModel model) {
        super();
        super.setVisible(true);
        super.setOpaque(false);
        this.tableModel = new TMTopSales(model.getEntities());
    }

    @Override
    public JPanel render() {
        ContainerBuilder builder = new ContainerBuilder().header("Best Sellers").description("Popular menu items.");
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel section = new JPanel(new GridBagLayout());
        section.setBackground(Styler.CONTAINER_BACKGROUND);
        int i = 0;

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        StandardTable table = new StandardTable(tableModel);
        table.setBackground(Styler.CONTAINER_BACKGROUND.brighter());
        table.getPanel().setMinimumSize(new Dimension(0, 100));
        table.getPanel().setPreferredSize(new Dimension(0, 150));
        table.getPanel().setMaximumSize(new Dimension(0, 300));
        section.add(table.getPanel(), GBC.setGBC(gbc, i, 0, 1.0));

        return builder.content(section).buildContainer();
    }
}
