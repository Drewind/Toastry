package Views.POS.Partials;

import Constants.ViewLayoutStyle;
import Controllers.ViewActionInterface;
import Entities.Product;
import Entities.ProductVariant;
import Entities.ProductVariantOption;
import Entities.ProductVariantSelected;
import Graphics.Builders.TextLabelBuilder;
import Graphics.Factories.TextFactory;
import Graphics.RoundedButton;
import Graphics.ViewPartial;
import Utilities.Styler;
import Views.PointSalesAction;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.text.DecimalFormat;

public class ProductVariantSelectionPartial extends ViewPartial {
    private final PointSalesAction viewAction;
    private final JLabel variantName = TextFactory.buildLabel("");
    private final JLabel variantCost = TextFactory.buildLabel("");
    private final JLabel variantRequired = TextFactory.buildLabel("");
    private final int sideMargins = 14;
//    private Product product;

    private final FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER) {{
        setHgap(30);
    }};
    private final JPanel variantOptions = new JPanel(flowLayout) {{
        setOpaque(false);
    }};

    public ProductVariantSelectionPartial(ViewActionInterface viewAction) {
        this.viewAction = (PointSalesAction) viewAction;
        final Insets variantNameMargins = new Insets(0, sideMargins, 0, sideMargins);
        add(variantName, layout.insets(variantNameMargins).layoutVertical());

        add(createForm(), layout.noMargins().layoutVertical());
    }

    private JPanel createForm() {
        final TextLabelBuilder textBuilder = new TextLabelBuilder();
        final Insets margins = new Insets(16, sideMargins, 16, sideMargins);

        return new ViewPartial(Styler.APP_BG_COLOR.brighter()) {{
            layout.insets(margins);

            add(newRow(
                    textBuilder.text("Additional Cost").buildLabel(),
                    variantCost,
                    ViewLayoutStyle.VERTICAL
            ), layout.layoutVertical());

            add(newRow(
                    textBuilder.text("Selection Options").buildLabel(),
                    variantOptions,
                    ViewLayoutStyle.HORIZONTAL
            ), layout.layoutVertical());
        }};
    }

    private JButton createOptionButton(final Product product, final ProductVariantOption variantOption) {
        JButton button = new RoundedButton(variantOption.getOptionName());
        button.addActionListener(evt -> viewAction.addToCheck(
                new ProductVariantSelected(product, variantOption.getVariant(), variantOption)
        ));
        return button;
    }

    public void updateView(Product product) {
        DecimalFormat df = new DecimalFormat("#.##");
        ProductVariant variant = product.getVariants().get(0);

        variantName.setText("Selection " + variant.getVariantName() + "");
        variantCost.setText("$" + df.format(variant.getVariantCost()));
        variantRequired.setText((variant.isSelectionRequired()) ? "Required" : "Optional");

        if (variant.getVariantCost() > 0) {
            variantCost.setForeground(Styler.DANGER_COLOR);
            variantCost.setFont(Styler.REGULAR_FONT.deriveFont(Font.BOLD));
        } else {
            variantCost.setForeground(Styler.TEXT_COLOR);
            variantCost.setFont(Styler.REGULAR_FONT);
        }

        variantOptions.removeAll();
        for (ProductVariantOption option : variant.getSelectionOptions()) {
            variantOptions.add(createOptionButton(product, option));
        }
    }
}
