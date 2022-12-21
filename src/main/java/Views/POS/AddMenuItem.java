package Views.POS;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import Controllers.ViewActionInterface;
import Entities.Product;
import Graphics.Factories.TextFactory;
import Graphics.ViewPartial;
import Utilities.GBC;
import Utilities.Styler;
import Views.POS.Partials.ProductVariantSelectionPartial;
import Views.PointSalesAction;

public class AddMenuItem extends ViewPartial {
    private final PointSalesAction viewAction;
    private final JLabel productName = TextFactory.buildStandardHeader("");
//    private final ProductVariantForm variantForm; // debug. trying out dto version
    private final ProductVariantSelectionPartial variantForm;

    public AddMenuItem(final ViewActionInterface viewAction) {
        super(new Color(61,61,61));
        this.variantForm = new ProductVariantSelectionPartial(viewAction);
//        this.variantForm = new ProductVariantForm(viewAction);
        this.viewAction = (PointSalesAction) viewAction;

        layout.fill(GridBagConstraints.BOTH);
        layout.anchor(GridBagConstraints.NORTH);

        add(createNavbar(), layout.layoutVertical());

        Insets margins = new Insets(24, 22, 0, 0);
        add(productName, layout.insets(margins).layoutVertical());
        Insets marginsVariant = new Insets(15, 0, 0, 0);
        add(variantForm, layout.insets(marginsVariant).layoutVertical());
        add(GBC.anchorPanel(), layout.anchorToBottom());
    }

    public void updateView(Product product) {
        productName.setText(product.getProductName());
        // we're passing in the first element for testing purposes
        this.variantForm.updateView(product);
    }

    private JPanel createNavbar() {
        Dimension navSize = new Dimension(90, 32);
        JButton backButton = new JButton("Cancel");
        backButton.setMinimumSize(navSize);
        backButton.setPreferredSize(navSize);
        backButton.setMaximumSize(navSize);
        backButton.setForeground(Styler.TEXT_COLOR);
        backButton.setBackground(Styler.DANGER_COLOR);
        backButton.addActionListener(e -> viewAction.switchToMenu());
        backButton.setBackground(Styler.CONTAINER_BACKGROUND.brighter());
        backButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        return new ViewPartial(Styler.CONTAINER_BACKGROUND) {{
            enforceViewHeight(32);
            add(backButton, layout.scalex(0.0).anchor(GridBagConstraints.WEST).layoutVertical());
            add(anchorPanel(), layout.anchorTo(GridBagConstraints.EAST));
        }};
    }
}
