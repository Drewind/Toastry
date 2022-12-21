package Controllers;

import javax.swing.JPanel;

import Interfaces.ControllerInterface;
import Views.NavbarViewAction;

/**
 * Handles the main navigation bar at the top of the screen.
 */
public class NavbarController implements ControllerInterface {
    private final NavbarViewAction creationView;

    public NavbarController() {

        // Controller actions
        this.creationView = new NavbarViewAction();
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}