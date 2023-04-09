package Views.POS.Partials;

import Controllers.PointOfSalesController;
import Controllers.ViewActionInterface;
import Entities.Product;
import Entities.ProductVariantSelected;
import Graphics.Factories.ButtonFactory;
import Graphics.Builders.TextLabelBuilder;
import Interfaces.ControllerInterface;
import Views.PointSalesAction;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class MenuItemPartial extends JPanel {
    private JButton clickablePane;
    private final PointOfSalesController controller;
    private final PointSalesAction viewAction;
    private final Product product;
    private JTextArea itemName = new JTextArea();

    public MenuItemPartial(final ControllerInterface controller, final ViewActionInterface viewAction, final Product product, final String menuItemName, final String menuItemQuantity, final String menuItemPrice) {
        super(new BorderLayout());
        super.setOpaque(true);
        super.setBackground(new Color(57, 110, 153));
        super.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(61,61,61)));
        this.controller = (PointOfSalesController) controller;
        this.viewAction = (PointSalesAction) viewAction;
        this.product = product;

        init(menuItemName, menuItemQuantity, menuItemPrice);

        add(this.clickablePane, BorderLayout.CENTER);
    }

    public void init(final String menuItemName, final String menuItemQuantity, final String menuItemPrice) {
        TextLabelBuilder builder = new TextLabelBuilder();
        Font font = new Font("Arial", Font.BOLD, 12);
        builder.fgColor(Color.WHITE);

        this.clickablePane = ButtonFactory.buildStandardButton(menuItemName, null, font);
        this.itemName = builder.text(menuItemName).font(font).buildTextArea();

//        this.clickablePane.addActionListener(e -> controller.addOrderToCheck(product));
        this.clickablePane.addActionListener(evt -> {
            if (this.product.getVariants().size() > 0)
                viewAction.switchToMenuItem(this.product);
            else
                viewAction.addToCheck(
                        new ProductVariantSelected(this.product)
                );
        });
    }
}
