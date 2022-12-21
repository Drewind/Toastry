package Entities;

/**
 * Finalized billing of a menu offering. Includes the base price of the product, any discounts,
 * and variant pricing. See below for an example.
 * Item         | Base Price | Discounts | Variant Pricing (i.e., Extra Sauce)
 * Sunken Ship  | 5.99       | 0.99      | 1.00
 *
 */
public class CheckLineItem extends Entity {
    private final Product product;
    private ProductVariantSelected variantSelected;
    private int quantity;

    public CheckLineItem(final Product product, ProductVariantSelected variantSelected) {
        this.product = product;
        this.variantSelected = variantSelected;
    }

    public Product getProduct() {
        return this.product;
    }

    public ProductVariantSelected getVariantSelected() {
        return this.variantSelected;
    }

    public void setVariant(ProductVariantSelected variantOption) {
        this.variantSelected = variantOption;
    }

    public int getQuantity() { return this.quantity; }
}
