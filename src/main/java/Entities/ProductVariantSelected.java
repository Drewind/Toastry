package Entities;

/**
 * Represents a choice for a product variant combination in the POS system.
 *
 * @TODO add in option pricing, https://toastry.atlassian.net/browse/TOAST-73
 */
public class ProductVariantSelected extends Entity {
    private final Product product;
    private final ProductVariant variant; // Acts as a foreign key to the variant
    private final ProductVariantOption variantOptionSelected;

    public ProductVariantSelected(final Product product, final ProductVariant variant, final ProductVariantOption variantOption) {
        this.product = product;
        this.variant = variant;
        this.variantOptionSelected = variantOption;
    }

    public ProductVariantSelected(final Product product) {
        this.product = product;
        this.variant = null;
        this.variantOptionSelected = null;
    }

    public Product getProduct() { return this.product; }
    public ProductVariant getVariant() { return this.variant; }
    public ProductVariantOption getVariantOptionSelected() { return this.variantOptionSelected; }
}
