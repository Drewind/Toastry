package Views.Location.Partials;

import Controllers.LocationController;
import Graphics.Factories.ButtonFactory;
import Interfaces.ControllerInterface;
import Utilities.GBC;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class NewLocationControls extends JPanel {
    private final LocationController controller;

    public NewLocationControls(ControllerInterface controller) {
        super(new GridBagLayout());
        this.controller = (LocationController) controller;
        super.setOpaque(false);
    }

    public JPanel renderPartial() {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel section = new JPanel(new GridBagLayout());
        section.setOpaque(false);

        JButton submitButton = ButtonFactory.buildSubmitButton("Add");
        JButton cancelButton = ButtonFactory.buildSubmitButton("Cancel");
        submitButton.addActionListener(e -> this.controller.submitProductForm());
        cancelButton.addActionListener(e -> this.controller.switchToHomeCreation());
        submitButton.setPreferredSize(new Dimension(0, 28));
        cancelButton.setPreferredSize(new Dimension(0, 28));
        cancelButton.setName("CancelLocationCreation");
        submitButton.setName("SubmitLocationCreation");

        gbc.fill = GridBagConstraints.HORIZONTAL;
        section.add(submitButton, GBC.setGBC(gbc, 0, 0, 0.5));
        section.add(cancelButton, GBC.setGBC(gbc, 1, 0, 0.5));

        return section;
    }
}
