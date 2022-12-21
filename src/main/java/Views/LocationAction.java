package Views;

import Controllers.LocationController;
import Controllers.ViewActionInterface;
import Entities.RentPeriod;
import Entities.Restaurant;
import Graphics.Builders.TextLabelBuilder;
import Interfaces.ControllerInterface;
import Interfaces.FormInterface;
import Interfaces.FormProcessorInterface;
import Models.Model;
import Models.RestaurantModel;
import Utilities.GBC;
import Utilities.Styler;
import Views.Location.Partials.NewLocationControls;
import Views.Location.CreateForm;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;

public class LocationAction implements ViewActionInterface, FormProcessorInterface {
    private final LocationController controller;
    private final RestaurantModel model;

    private final FormInterface<Restaurant> form = new CreateForm();

    public LocationAction(ControllerInterface controller, Model model) {
        this.controller = (LocationController) controller;
        this.model = (RestaurantModel) model;
    }

    @Override
    public JPanel renderView() {
        // Set constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = GBC.getDefaultLayoutMargins();

        NewLocationControls buttonPanel = new NewLocationControls(controller);

        return new JPanel(new GridBagLayout()) {{
            setOpaque(false);
            add(headerText(), GBC.setGBC(gbc, 0, 0, 1.0));
            add(form.getView(), GBC.setGBC(gbc, 0, 1, 1.0));
            add(buttonPanel.renderPartial(), GBC.setGBC(gbc, 0, 2, 1.0));
            add(GBC.anchorPanel(), GBC.setToAnchorBottom(gbc, 0, 3, 2));
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

        panel.add(textBuilder.text("NEW LOCATION").fgColor(Styler.THEME_COLOR).fontSize(26).buildLabel(), GBC.setGBC(gbc, 0, 0, 1.0));
        gbc.insets = new Insets(0, GBC.getDefaultLayoutMargins().left, 5, 0);

        panel.add(textBuilder.text("Add a new location below.").fgColor(Styler.TEXT_COLOR).fontSize(18).buildLabel(), GBC.setGBC(gbc, 0, 1, 1.0));

        return panel;
    }

    /**
     * Called from the button control partial view to request a new location
     * be persisted. This method will request the form's inputs and use it
     * to construct a location entity. Should any input be null or missing,
     * this method will call {@code invalidateForm} on the form.
     */
    @Override
    public void processForm() {
        List<String> inputs = form.getInputs();


        try {
            Restaurant location = new Restaurant(
                    inputs.get(0),
                    Double.parseDouble(inputs.get(1)),
                    RentPeriod.getEnum(inputs.get(2))
            );

            if (!this.controller.addLocation(location)) {
                form.invalidateForm();
            }

            form.resetView();
        } catch (Exception ex) {
            form.invalidateForm();
        }
    }
}
