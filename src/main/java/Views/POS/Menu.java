package Views.POS;

import Controllers.PointOfSalesController;
import Controllers.ViewActionInterface;
import Entities.Product;
import Graphics.ModernScrollBar;
import Interfaces.ControllerInterface;
import Utilities.GBC;
import Utilities.Styler;
import Views.POS.Partials.MenuItemPartial;
import Views.POS.Partials.MenuNavbar;
import Views.PointSalesAction;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

public class Menu extends JPanel {
    private JScrollPane scrollPanel;
    private final JPanel itemContainer = new JPanel(new GridLayout(0, 3, 30, 30));
    private final List<Product> products;
    private final PointOfSalesController controller;
    private final PointSalesAction viewAction;

    public Menu(final ControllerInterface controller, final ViewActionInterface viewAction, List<Product> products) {
        super(new GridBagLayout());
        super.setOpaque(true);
        super.setBackground(new Color(61, 61, 61));
        super.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Styler.CONTAINER_BACKGROUND));
        this.controller = (PointOfSalesController) controller;
        this.viewAction = (PointSalesAction) viewAction;

        // Setting these size constraints seems to screw up the CheckOrderPartial
        Dimension PANEL_SIZE = new Dimension(350, 0);
        super.setMinimumSize(PANEL_SIZE);
        super.setPreferredSize(PANEL_SIZE);
        super.setMaximumSize(PANEL_SIZE);
        this.products = products;

        // Set constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;

        createMenuOfferings();
        gbc.ipadx = 10;
        gbc.ipady = 10;
        super.add(createNavbar(), GBC.setGBC(gbc, 0, 0, 0.0));
        super.add(this.scrollPanel, GBC.setGBC(gbc, 0, 1, 1.0, 1.0));
    }

    public JPanel createNavbar() {
        return new MenuNavbar();
    }

    public void createMenuOfferings() {
        this.scrollPanel = new ModernScrollBar(this.itemContainer);

        this.itemContainer.setBackground(Styler.CONTAINER_BACKGROUND.brighter());
        this.itemContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        for (Product product : this.products) {
            this.itemContainer.add(new MenuItemPartial(controller, viewAction, product, product.getProductName(), "5", "7"));
        }
    }
}
