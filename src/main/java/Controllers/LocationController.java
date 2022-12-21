package Controllers;

import Constants.ActiveController;
import Constants.LogLevel;
import Entities.Restaurant;
import Interfaces.ControllerInterface;
import Models.Model;
import Views.LocationAction;
import Views.Popups.Popup;

import javax.swing.JPanel;
import java.awt.CardLayout;

/**
 * Handles interactions with restaurant locations.
 */
public class LocationController implements ControllerInterface {
    private final Model model;
    private final LocationAction creationView;
    private final JPanel contentPane;

    public LocationController(Model model, final JPanel contentPane) {
        this.model = model;
        this.contentPane = contentPane;

        // Views
        this.creationView = new LocationAction(this, model);
        this.creationView.renderView();
    }

    /**
     * Switches controller to HomeController. By default, will show the default view
     * assigned to <b>HomeController</b>.
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
     * @param restaurant Product
     * @return {@code boolean}
     */
    public boolean addLocation(Restaurant restaurant) {
        try {
            this.model.addEntity(restaurant);
            Popup.infoBox("Success!", "Location created and saved!");
            return true;
        } catch (NoSuchFieldException ex) {
            System.out.println(LogLevel.WARNING + "An error occurred when persisting location (" + restaurant.getID() + ") in model.\n\t" + ex);
            Popup.infoBox("Error!", "No ");
            return false;
        } catch(Exception ex) {
            Popup.infoBox("Error!", "Unfortunately, an exception occurred while saving.");
            return false;
        }
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}