package Views.Home.Partials;

import Controllers.HomeController;
import Graphics.Factories.ButtonFactory;
import Interfaces.ControllerInterface;
import Interfaces.ViewPartialInterface;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;

/**
 * Displays a row of control buttons. Probably will remove this.
 */
public class DashboardControls extends JPanel implements ViewPartialInterface {
    private final HomeController controller;

    public DashboardControls(final ControllerInterface controller) {
        super();
        setVisible(true);
        setOpaque(false);
        this.controller = (HomeController) controller;
    }

    @Override
    public JPanel render() {
        // Button to go back to home screen
        JButton newProductButton = ButtonFactory.buildSubmitButton("Add Product");
        newProductButton.addActionListener(e -> this.controller.switchToProductCreation());
        newProductButton.setPreferredSize(new Dimension(200, 28));
        add(newProductButton);

        JButton newLocationButton = ButtonFactory.buildSubmitButton("Add Restaurant");
        newLocationButton.addActionListener(e -> this.controller.switchToLocationCreation());
        newLocationButton.setPreferredSize(new Dimension(200, 28));
        add(newLocationButton);

        JButton posView = ButtonFactory.buildSubmitButton("Add Sales");
        posView.addActionListener(e -> this.controller.switchToPOS());
        posView.setPreferredSize(new Dimension(200, 28));
        add(posView);

        return this;
    }
}
