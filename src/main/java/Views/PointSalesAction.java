package Views;

import Controllers.PointOfSalesController;
import Controllers.ViewActionInterface;
import Entities.Check;
import Entities.Product;
import Entities.CheckLineItem;
import Entities.ProductVariantSelected;
import Graphics.Builders.TextLabelBuilder;
import Interfaces.ControllerInterface;
import Models.EntityModel;
import Utilities.GBC;
import Utilities.Styler;
import Views.POS.AddMenuItem;
import Views.POS.CheckPartial;
import Views.POS.Menu;
import Views.POS.Partials.OrderPartial;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;

public class PointSalesAction implements ViewActionInterface {
    private final JPanel ordersPane = new JPanel(new GridBagLayout()) {{
        setBackground(new Color(61,61,61));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }};
    private final PointOfSalesController controller;
    private final EntityModel<Product> model;
    private final CheckPartial checkPartial;
    private final AddMenuItem addMenuItem;
    private final HashMap<Product, Integer> productsOnOrder = new HashMap<>();
    private final JPanel tabbedPanel = new JPanel(new CardLayout());
    private int counter = 0;
    private Check check = new Check();
    private Product productSelected;

    public PointSalesAction(ControllerInterface controller, EntityModel<Product> model) {
        this.controller = (PointOfSalesController) controller;
        this.model = model;
        this.checkPartial = new CheckPartial(controller, this.ordersPane);
        this.addMenuItem = new AddMenuItem(this);

        tabbedPanel.add(new Menu(controller, this, this.model.getEntities()), "Menu");
        tabbedPanel.add(addMenuItem, "AddMenuItem");
        tabbedPanel.setOpaque(false);
    }

    @Override
    public JPanel renderView() {
        // Set constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = GBC.getDefaultLayoutMargins();

        return new JPanel(new GridBagLayout()) {{
            setOpaque(false);
            add(checkPartial, GBC.setGBC(gbc, 0, 0, 0, 1.0));

            gbc.fill = GridBagConstraints.BOTH;
//            add(new Menu(controller, model.getEntities()), GBC.setGBC(gbc, 1, 0, 1.0));
            add(tabbedPanel, GBC.setGBC(gbc, 1, 0, 1.0));
        }};
    }

    /**
     * When a menu offering is added to the customer's order, this view action will add
     * the product to the order partial view. Once rendered, the menu offering will be
     * added to the {@code productsOnOrder hashmap}.
     *
     * If the product already exists in the hashmap, then we increment the quantity value.
     * @todo will need to refactor according to <a href="https://toastry.atlassian.net/browse/TOAST-52">TOAST-52</a>
     */
//    public void renderItemToOrder(Product) {
//        // TODO add in logic to support https://toastry.atlassian.net/browse/TOAST-52
//        addItem(transactionItem);
//        this.productsOnOrder.put(transactionItem.getProduct(), productsOnOrder.getOrDefault(transactionItem.getProduct(), 0) + 1);
//    }

    public void addToCheck(ProductVariantSelected variantSelected) {
        System.out.println("[ DEBUG ] PointSalesAction: adding a new CheckLineItem to check."
            + "\n\t* Product:  " + variantSelected.getProduct().getProductName()
            + "\n\t* Variant:  " + (variantSelected.getVariant() != null ? variantSelected.getVariant().getVariantName() : "null")
            + "\n\t* Selected: " + (variantSelected.getVariant() != null ? variantSelected.getVariantOptionSelected().getOptionName() : "null")
        );
        this.check.addLineItem(new CheckLineItem(variantSelected.getProduct(), variantSelected));
        addItem(new CheckLineItem(variantSelected.getProduct(), variantSelected));
    }

    public void switchToMenu() {
        CardLayout cl = (CardLayout)this.tabbedPanel.getLayout();
        cl.show(this.tabbedPanel, "Menu");
    }

    public void switchToMenuItem(Product product) {
        //AddMenuItemVM viewModel = new AddMenuItemVM(product, product.getVariants());
        this.addMenuItem.updateView(product);
        CardLayout cl = (CardLayout)this.tabbedPanel.getLayout();
        cl.show(this.tabbedPanel, "AddMenuItem");
    }

    /**
     * Used to retrieve a table's check.
     * @return {@code HashMap<Product, Integer>}
     */
    public Check getTableCheck() {
        return this.check;
    }

    private void addItem(CheckLineItem checkLineItem) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 12, 0);

        if (counter > 0) {
            this.ordersPane.remove(--counter);
        }

//        this.ordersPane.add(new OrderPartial(Integer.toString(counter), checkLineItem), GBC.setGBC(gbc, 0, ++counter, 1.0));
        this.ordersPane.add(new OrderPartial(Integer.toString(counter), checkLineItem), GBC.setGBC(gbc, 0, ++counter, 1.0));
        this.ordersPane.add(GBC.anchorPanel(), GBC.setToAnchorBottom(gbc, 0, ++counter, 2));

        this.ordersPane.validate();
        this.checkPartial.validate();
    }

    /**
     * Small partial view to generate the headers. Since this method is small,
     * decided to place it inside the view action partial.
     * @return JPanel
     */
    private JPanel headerText() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        TextLabelBuilder textBuilder = new TextLabelBuilder();
        GridBagConstraints gbc = new GridBagConstraints();

        panel.add(textBuilder.text("Point of Sales").fgColor(Styler.THEME_COLOR).fontSize(26).buildLabel(), GBC.setGBC(gbc, 0, 0, 1.0));
        gbc.insets = new Insets(0, GBC.getDefaultLayoutMargins().left, 5, 0);

        panel.add(textBuilder.text("Add a new product below.").fgColor(Styler.TEXT_COLOR).fontSize(18).buildLabel(), GBC.setGBC(gbc, 0, 1, 1.0));

        return panel;
    }
}
