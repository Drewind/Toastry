package Controllers;

import Constants.ActiveController;
import Entities.Check;
import Interfaces.ControllerInterface;
import Models.EntityModel;
import Models.ProductModel;
import Services.TransactionService;
import Views.PointSalesAction;

import javax.swing.JPanel;
import java.awt.CardLayout;

/**
 * Handles interactions with the point-of-sales (POS) screen.
 */
public class PointOfSalesController implements ControllerInterface {
    private final PointSalesAction creationView;
    private final JPanel contentPane;
    private final TransactionService transactionService;

    public PointOfSalesController(ProductModel productModel, EntityModel transactionModel, final JPanel contentPane) {
        this.contentPane = contentPane;
        this.transactionService = new TransactionService(productModel, transactionModel);

        // Views
        this.creationView = new PointSalesAction(this, productModel);
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

    public void checkoutOrder() {
        System.out.println("[ DEBUG ] POS Controller: user has triggered a checkout on a order.");
        Check check = this.creationView.getTableCheck();
//        this.transactionService.processTransaction(new Check(products, 1));
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}