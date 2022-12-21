package Entities;

/**
 * This represents an available option for a given product variant ({@code ProductVariant}).
 * Imagine a hot wing offering and the ability to select a sauce with three options available: hot, mild, and BBQ.
 * This object would be one of those three options.
 *
 * @TODO add in option pricing, https://toastry.atlassian.net/browse/TOAST-73
 */
public class ProductVariantOption extends Entity {
    private ProductVariant variant; // Acts as a foreign key to the variant
    private String optionName;

    public ProductVariantOption(final ProductVariant variant, final String optionName) {
        super();
        this.variant = variant;
        this.optionName = optionName;
        setSerializationChain();
    }

    public ProductVariantOption(final String ID) { super(ID); setSerializationChain(); }

    /**
     * Functions to call in order to safely modify the text file database.
     */
    @Override
    protected void setSerializationChain() {
        this.serializationChain.add(super::getID);
        this.serializationChain.add(() -> variant.getID());
        this.serializationChain.add(this::getOptionName);
    }

    public ProductVariant getVariant() { return this.variant; }

    public String getOptionName() { return this.optionName; }

    public void setParentVariant(final ProductVariant variant) {
        this.variant = variant;
    }

    public void setOptionName(final String optionName) {
        this.optionName = optionName;
    }
}
