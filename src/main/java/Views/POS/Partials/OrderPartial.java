package Views.POS.Partials;

import Entities.CheckLineItem;
import Graphics.Builders.TextLabelBuilder;
import Graphics.Controls.SpinnerInput;
import Graphics.Factories.TextFactory;
import Graphics.ViewPartial;
import Utilities.GBC;
import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.*;

public class OrderPartial extends ViewPartial {
    private final JSpinner itemCount = new SpinnerInput(new SpinnerNumberModel(1, 0, 99, 1));
    private final JLabel itemNumberLabel = TextFactory.buildLabel("#");
    private final JLabel itemNameLabel = TextFactory.buildLabel("");
    private final JLabel variantLabel = TextFactory.buildLabel("");
    private final CheckLineItem checkLineItem;

    public OrderPartial(final String itemNumber, final CheckLineItem checkLineItem) {
        super(new Color(61,61,61).brighter());
        super.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Styler.CONTAINER_BACKGROUND));
        super.setName(checkLineItem.getProduct().getProductName());
        this.checkLineItem = checkLineItem;
        this.itemNumberLabel.setText("#" + itemNumber);
        this.itemNameLabel.setText(checkLineItem.getProduct().getProductName());
        this.variantLabel.setText(buildVariantName());
        createLayout();
    }

    private void createLayout() {
        // Defaults
        layout.anchor(GridBagConstraints.WEST);
        layout.scaley(1.0);
        layout.padX(15);

        // Item Number
        enforceSize(itemNumberLabel, 20, 30);
        layout.scalex(0.0);
        layout.fill(GridBagConstraints.VERTICAL);
        add(itemNumberLabel, layout.layoutCustom(layout._gridx++, 0));

        // Item Name
        layout.fill(GridBagConstraints.BOTH);
        layout.scalex(1.0);
        layout.scaley(0.0);
        add(itemNameLabel, layout.layoutCustom(layout._gridx++, 0));

        // Spinner
        enforceWidth(itemCount, 40);
        layout.scalex(0.0);
        layout.scaley(1.0);
        layout.anchor(GridBagConstraints.EAST);
        layout.fill(GridBagConstraints.VERTICAL);
        add(itemCount, layout.layoutCustom(layout._gridx, 0));

        // Variants
        layout.anchor(GridBagConstraints.CENTER);
        layout.fill(GridBagConstraints.HORIZONTAL);
        layout.scalex(1.0);
        layout.scaley(0.0);
        layout.gridWidth(3);
        variantLabel.setForeground(Styler.DANGER_COLOR);
        add(variantLabel, layout.layoutCustom(0, 1));
    }

    private String buildVariantName() {
        return (this.checkLineItem.getVariantSelected().getVariant() != null
                ? this.checkLineItem.getVariantSelected().getVariant().getVariantName() + ": "
                + this.checkLineItem.getVariantSelected().getVariantOptionSelected().getOptionName()
                : "");
    }

    private JSeparator createSeparator() {
        return new JSeparator() {{
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(61,61,61)));
        }};
    }
}
