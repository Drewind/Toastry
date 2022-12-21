package Models.ViewModels;

import Entities.ProductVariant;
import Graphics.Factories.TextFactory;

import javax.swing.JLabel;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;

public class VariantCreationVM {
    final LinkedHashMap<String, ArrayList<JLabel>> components = new LinkedHashMap<>() {{
        put("VariantName", new ArrayList<>() {{
            add(TextFactory.buildLabel("Variant Name:"));
            add(TextFactory.buildLabel("none selected"));
        }});
        put("VariantCost", new ArrayList<>() {{
            add(TextFactory.buildLabel("Additional Cost:"));
            add(TextFactory.buildLabel("$0.00"));
        }});
        put("VariantRequired", new ArrayList<>() {{
            add(TextFactory.buildLabel("Selection Required:"));
            add(TextFactory.buildLabel("optional"));
        }});
        put("VariantType", new ArrayList<>() {{
            add(TextFactory.buildLabel("Variant Type:"));
            add(TextFactory.buildLabel("boolean"));
        }});
        put("VariantOptions", new ArrayList<>() {{
            add(TextFactory.buildLabel("Selection Options:"));
            add(TextFactory.buildLabel("N/A"));
        }});
    }};

    public Collection<ArrayList<JLabel>> getComponents() {
        return this.components.values();
    }

    public void updateComponents(final ProductVariant variant) {
        String variantRequired = (variant.isSelectionRequired() ? "required" : "optional");
        String variantOptions = String.join(",", Arrays.toString(variant.getSelectionOptions().toArray()));

        String formatCostField = NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(variant.getVariantCost());
        this.components.get("VariantName").get(1).setText(variant.getVariantName());
        this.components.get("VariantCost").get(1).setText(formatCostField);
        this.components.get("VariantRequired").get(1).setText(variantRequired);
        this.components.get("VariantType").get(1).setText(variant.getSelectionType());
        this.components.get("VariantOptions").get(1).setText(variantOptions);
    }
}
