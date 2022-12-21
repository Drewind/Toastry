package Views;

import Constants.LogLevel;
import Controllers.ProductController;
import Controllers.ViewActionInterface;
import Entities.Product;
import Entities.ProductCategory;
import Entities.ProductVariant;
import Interfaces.ControllerInterface;
import Interfaces.FormInterface;
import Interfaces.FormProcessorInterface;
import Models.EntityModel;
import Models.ProductModel;
import Models.ProductVariantModel;
import Utilities.GBC;
import Utilities.Styler;
import Graphics.Builders.TextLabelBuilder;
import Views.ProductView.CreateGeneralForm;
import Views.ProductView.CreateNavbar;
import Views.ProductView.CreateVariantForm;
import Views.ProductView.Partials.NewProductButtons;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Product creation view action. Handles all necessary interactions between the user and the controller. From button
 * clicks, rendering partials, etc. this view action will serve that logic.
 */
public class ProductCreationAction implements ViewActionInterface, FormProcessorInterface {
    private final ProductController controller;
    private final ProductVariantModel variantModel;
    private final FormInterface<Product> generalForm = new CreateGeneralForm();
    private final FormInterface<ProductVariant> variantForm;
    private final JPanel tabbedPanel = new JPanel(new CardLayout());

    public ProductCreationAction(ControllerInterface controller, ProductVariantModel variantModel) {
        this.controller = (ProductController) controller;
        this.variantModel = variantModel;
        this.variantForm = new CreateVariantForm(variantModel);
    }

    @Override
    public JPanel renderView() {
        // Set constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = GBC.getDefaultLayoutMargins();

        NewProductButtons buttonPanel = new NewProductButtons(controller);

        return new JPanel(new GridBagLayout()) {{
            setOpaque(false);
            add(headerText(), GBC.setGBC(gbc, 0, 0, 1.0));
            add(tabbedPanel(), GBC.setGBC(gbc, 0, 1, 1.0, 1.0));
            add(buttonPanel.renderPartial(), GBC.setGBC(gbc, 0, 2, 1.0));
        }};
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

        panel.add(textBuilder.text("NEW PRODUCT").fgColor(Styler.THEME_COLOR).fontSize(26).buildLabel(), GBC.setGBC(gbc, 0, 0, 1.0));
        gbc.insets = new Insets(0, GBC.getDefaultLayoutMargins().left, 5, 0);

        panel.add(textBuilder.text("Add a new product below.").fgColor(Styler.TEXT_COLOR).fontSize(18).buildLabel(), GBC.setGBC(gbc, 0, 1, 1.0));

        return panel;
    }

    private JPanel tabbedPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;

        tabbedPanel.add(generalForm.getView(), "General");
        tabbedPanel.add(variantForm.getView(), "Variants");
        tabbedPanel.setBackground(Styler.CONTAINER_BACKGROUND);

        panel.add(new CreateNavbar(this).renderView(), GBC.setGBC(gbc, 0, 0, 1.0));
        panel.add(tabbedPanel, GBC.setGBC(gbc, 0, 1, 1.0));
        panel.add(GBC.anchorPanel(), GBC.setToAnchorBottom(2));
        return panel;
    }

    public void viewGeneral() {
        CardLayout cl = (CardLayout)this.tabbedPanel.getLayout();
        cl.show(this.tabbedPanel, "General");
    }

    public void viewVariants() {
        CardLayout cl = (CardLayout)this.tabbedPanel.getLayout();
        cl.show(this.tabbedPanel, "Variants");
    }

    /**
     * Called from the button control partial view to request a new product
     * be persisted. This method will request the form's inputs and use it
     * to construct a product entity. Should any input be null or missing,
     * this method will call {@code invalidateForm} on the form.
     */
    @Override
    public void processForm() {
        List<String> inputs = generalForm.getInputs();
        List<ProductVariant> variants = variantForm.getInputs().stream()
                .filter(variantModel::entityExists)
                .map(variantModel::retrieve)
                .toList();

        try {
            Product product = new Product(
                    inputs.get(0),
                    Double.parseDouble(inputs.get(1)),
                    Double.parseDouble(inputs.get(2)),
                    ProductCategory.getEnum(inputs.get(3))
            );

            product.setVariants(variants);

            if (!this.controller.addProduct(product)) {
                generalForm.invalidateForm();
            }

            generalForm.resetView();
            variantForm.resetView();
        } catch (Exception ex) {
            generalForm.invalidateForm();
            JOptionPane.showMessageDialog(null,"An unexpected exception was thrown: "
                    + ex);
            System.out.println(LogLevel.WARNING + "Unexpected exception thrown when processing new product form.\n" + ex);
        }
    }
}
