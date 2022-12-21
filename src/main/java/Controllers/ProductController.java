package Controllers;

import java.awt.CardLayout;

import javax.swing.JPanel;

import Constants.ActiveController;
import Constants.LogLevel;
import Entities.Product;
import Entities.ProductVariant;
import Interfaces.ControllerInterface;
import Models.EntityModel;
import Models.ProductModel;
import Models.ProductVariantModel;
import Views.Popups.Popup;
import Views.ProductCreationAction;

/**
 * Handles managing products in Toastry.
 */
public class ProductController implements ControllerInterface {
    private final EntityModel<Product> productModel;
    private final EntityModel<ProductVariant> variantModel;
    private final ProductCreationAction creationView;
    private final JPanel contentPane;

    public ProductController(ProductModel productModel, ProductVariantModel variantModel, final JPanel contentPane) {
        this.productModel = productModel;
        this.variantModel = variantModel;
        this.contentPane = contentPane;

        // Views
        this.creationView = new ProductCreationAction(this, variantModel);
        this.creationView.renderView();
    }

    /**
     * Switches controller to HomeController. By default, will show the default view
     * assigned to HomeController.
     */
    public void switchToHomeCreation() {
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, ActiveController.HOME.toString());
    }

    /**
     * Calls the view action to process the new product form. Once the
     * view action is finished processing the form, it will call {@code addProduct}
     * on the controller to finish persisting the new entity.
     */
    public void submitProductForm() {
        this.creationView.processForm();
    }

    /**
     * Saves a new product to the database. Called from a view action with
     * a product entity passed in to this method.
     *
     * Will attempt to persist the entity. If the operation fails, this method
     * returns {@code false} so that the view action class can alert the user.
     * @param product Product
     * @return {@code boolean}
     */
    public boolean addProduct(Product product) {
        try {
            this.productModel.insert(product);
            Popup.infoBox("Success!", "Product created and saved!");
            return true;
        } catch(Exception ex) {
            System.out.println(LogLevel.WARNING + "Could not persist new product.\n\t> " + ex);
            Popup.infoBox("Error!", "Unfortunately, an exception occurred while saving.");
            return false;
        }
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}