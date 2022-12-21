package Views.ProductView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.*;

import Constants.LogLevel;
import Entities.Product;
import Entities.ProductCategory;
import Graphics.Controls.DropdownInput;
import Graphics.Controls.NumberInput;
import Graphics.Controls.TextInput;
import Graphics.Builders.TextLabelBuilder;
import Graphics.Factories.TextFactory;
import Interfaces.FormInterface;
import Interfaces.InputObserver;
import Utilities.GBC;
import Utilities.Styler;
import Utilities.SwingObjectText;
import Graphics.InputDecimalFilter;

public class CreateGeneralForm extends JPanel implements FormInterface<Product>, InputObserver {
    private final InputDecimalFilter docFilter = new InputDecimalFilter();
    private final JLabel errors = TextFactory.buildFormErrorMessage();
    private final JLabel errorHeader = TextFactory.buildFormErrorHeader();

    private final TextInput nameField = new TextInput(false) {{
       setPreferredSize(new Dimension(0, 30));
       setName("ProductName");
    }};

    private final JLabel uidField;

    private final NumberInput priceField = new NumberInput(docFilter) {{
        setPreferredSize(new Dimension(0, 30));
        setName("ProductPrice");
    }};

    private final NumberInput costField = new NumberInput(docFilter) {{
        setPreferredSize(new Dimension(0, 30));
        setName("ProductCost");
    }};

    private final DropdownInput<ProductCategory> categoryField = new DropdownInput<>(ProductCategory.values()) {{
        setPreferredSize(new Dimension(0, 30));
        setName("ProductCategory");
    }};

    private final ArrayList<JComponent> FIELDS = new ArrayList<>() {{
        add(nameField);
        add(costField);
        add(priceField);
        add(categoryField);
    }};

    public CreateGeneralForm() {
        super(new GridBagLayout());
        super.setOpaque(false);

        registerObservers();

        final TextLabelBuilder textBuilder = new TextLabelBuilder();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(16, 20, 0, 20);

        uidField = textBuilder.fontSize(18).buildLabel();

        super.add(newRow(
                errorHeader,
                errors
        ), GBC.setGBC(gbc, 0, 0, 1.0));

        //_______________
        super.add(newRow(
                textBuilder.text("Product Name").fontSize(18).buildLabel(),
                nameField
        ), GBC.setGBC(gbc, 0, 1, 1.0));

        super.add(newRow(
                textBuilder.text("UID").fontSize(18).buildLabel(),
                uidField
        ), GBC.setGBC(gbc, 1, 1, 1.0));

        //_______________
        super.add(newRow(
                textBuilder.text("Selling Price").fontSize(18).buildLabel(),
                priceField
        ), GBC.setGBC(gbc, 0, 2, 0.5));

        super.add(newRow(
                textBuilder.text("Product Cost").fontSize(18).buildLabel(),
                costField
        ), GBC.setGBC(gbc, 1, 2, 0.5));

        //_______________
        super.add(newRow(
                textBuilder.text("Category").fontSize(18).buildLabel(),
                categoryField
        ), GBC.setGBC(gbc, 0, 3, 1.0));
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
     *
     * @return HashMap<String, Object>
     */
    @Override
    public List<String> getInputs() {
        return this.FIELDS.stream().map(SwingObjectText::getSwingObjectText).collect(Collectors.toList());
    }

    @Override
    public void updateView(final Product product) {
        this.nameField.setText(product.getProductName());
        this.priceField.setText(Double.toString(product.getPrice()));
        this.categoryField.setSelectedItem(product.getCategory());

        this.revalidate();
    }

    @Override
    public void resetView() {
        for (JComponent component : this.FIELDS) {
            if (component instanceof NumberInput)
                SwingObjectText.setSwingObjectText(component, "0.00");
            else
                SwingObjectText.setSwingObjectText(component, "");
        }
        this.categoryField.setSelectedItem(ProductCategory.MAIN_DISH);
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
