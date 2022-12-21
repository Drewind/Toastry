package Controllers;

import java.awt.CardLayout;

import javax.swing.JPanel;

import Constants.ActiveController;
import Interfaces.ControllerInterface;
import Models.DailyStatsModel;
import Models.ProductModel;
import Views.HomeAction;

/**
 * Handles the home screen. Implements the standard ControllerInterface.
 */
public class HomeController implements ControllerInterface {
    private final HomeAction creationView;
    private final JPanel contentPane;
    private final DailyStatsModel statsModel;
    private final ProductModel productModel;

    /**
     * Constructs a new HomeController controller.
     * @param productModel a reference to a IModelInterface with class <Product>
     * @param statsModel a reference a DailyStatsModel object
     * @param contentPane a reference to the central content pane
     */
    public HomeController(
        ProductModel productModel,
        DailyStatsModel statsModel,
        JPanel contentPane) {
        
        this.contentPane = contentPane;
        this.statsModel = statsModel;
        this.productModel = productModel;

        // Views
        this.creationView = new HomeAction(this, productModel);
        this.creationView.renderView();
    }

    /**
     * Switches the view to the product creation view.
     */
    public void switchToProductCreation() {
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, ActiveController.PRODUCT.toString());
    }

    public void switchToLocationCreation() {
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, ActiveController.LOCATION.toString());
    }

    public void switchToPOS() {
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, ActiveController.POS.toString());
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}