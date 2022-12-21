package Views.Location;

import Entities.RentPeriod;
import Entities.Restaurant;
import Graphics.Controls.DropdownInput;
import Graphics.Controls.NumberInput;
import Graphics.Controls.TextInput;
import Graphics.InputDecimalFilter;
import Graphics.Builders.TextLabelBuilder;
import Interfaces.FormInterface;
import Interfaces.InputObserver;
import Utilities.GBC;
import Utilities.Styler;
import Utilities.SwingObjectText;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.*;
import java.util.stream.Collectors;

public class CreateForm extends JPanel implements FormInterface<Restaurant>, InputObserver {
    private final InputDecimalFilter docFilter = new InputDecimalFilter();

    private final JLabel errors;

    private final JLabel errorHeader;

    private final TextInput nameField = new TextInput(false) {{
       setPreferredSize(new Dimension(0, 30));
       setName("RestaurantName");
    }};

    private final NumberInput rentField = new NumberInput(docFilter) {{
        setPreferredSize(new Dimension(0, 30));
        setName("RestaurantRent");
    }};

    private final DropdownInput<RentPeriod> rentPeriodField = new DropdownInput<>(RentPeriod.values()) {{
        setPreferredSize(new Dimension(0, 30));
        setName("RestaurantRentPeriod");
    }};

    private final ArrayList<JComponent> FIELDS = new ArrayList<>() {{
        add(nameField);
        add(rentField);
        add(rentPeriodField);
    }};

    public CreateForm() {
        super(new GridBagLayout());
        super.setBackground(Styler.CONTAINER_BACKGROUND);

        registerObservers();

        final TextLabelBuilder textBuilder = new TextLabelBuilder();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(16, 20, 0, 20);

        errors = textBuilder.fgColor(Styler.TEXT_COLOR).fontSize(16).buildLabel();
        errorHeader = textBuilder.text("ERRORS").fgColor(Styler.DANGER_COLOR).fontSize(20).buildLabel();
        errorHeader.setVisible(false);
        textBuilder.fgColor(Styler.TEXT_COLOR);

        super.add(newRow(
                errorHeader,
                errors
        ), GBC.setGBC(gbc, 0, 0, 1.0));

        //_______________
        super.add(newRow(
                textBuilder.text("Restaurant Name").fontSize(18).buildLabel(),
                nameField
        ), GBC.setGBC(gbc, 0, 1, 1.0));

        //_______________
        super.add(newRow(
                textBuilder.text("Rent Amount").fontSize(18).buildLabel(),
                rentField
        ), GBC.setGBC(gbc, 0, 2, 0.5));

        super.add(newRow(
                textBuilder.text("Rent Due").fontSize(18).buildLabel(),
                rentPeriodField
        ), GBC.setGBC(gbc, 1, 2, 1.0));
    }

    private void registerObservers() {
        this.nameField.registerObserver(this);
    }

    /**
     * Creates a simple transparent JPanel to add two components together.
     * @param elementA JComponent
     * @param elementB JComponent
     * @return JPanel
     */
    private JPanel newRow(JComponent elementA, JComponent elementB) {
        return new JPanel(new BorderLayout()) {{
            setOpaque(false);
            add(elementA, BorderLayout.NORTH);
            add(elementB, BorderLayout.CENTER);
        }};
    }

    /**
     * Retrieves the form's input values and stores them as objects inside a HashMap.
     * If a value is null or empty, excludes that value from the returned results.
     * @return HashMap<String, Object>
     */
    @Override
    public List<String> getInputs() {
        return this.FIELDS.stream().map(SwingObjectText::getSwingObjectText).collect(Collectors.toList());
    }

    @Override
    public void updateView(final Restaurant restaurant) {
    }

    @Override
    public void resetView() {
        for (JComponent component : this.FIELDS) {
            if (component instanceof NumberInput)
                SwingObjectText.setSwingObjectText(component, "0");
            else
                SwingObjectText.setSwingObjectText(component, "");
        }
    }

    @Override
    public void invalidateForm() {
        this.errorHeader.setVisible(true);
        this.errors.setText("Could not save entity! One or more fields were invalid.");
        super.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Styler.DANGER_COLOR));
    }

    @Override
    public JPanel getView() {
        return this;
    }

    @Override
    public void notifyFocusGained() {
        super.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.errors.setText("");
        this.errorHeader.setVisible(false);
    }

    @Override
    public void notifyFocusLost() {
        super.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.errors.setText("");
        this.errorHeader.setVisible(false);
    }

    @Override
    public void notifyInputChanged(Object value) {
        //
    }
}
