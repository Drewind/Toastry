package Entities;

import Constants.LogLevel;
import Services.IDGenerator;

import java.util.ArrayList;
import java.util.List;


/**
 * For the {@code ProductVariantOption} code, I went with storing the ID of the product variant option selected by
 * the user to avoid potential runtime unchecked exceptions. If a user were to delete an option during runtime which
 * was stored in this object, we <i>could</i> have encountered an unchecked exception. For this reason we are storing
 * the ID of the option so that we can check with the model when we are serializing and deserializing objects.
 */
public class ProductVariant extends Entity {
    private String variantName;
    private double variantCost;
    private boolean selectionRequired;
    private String selectionType;
    private List<ProductVariantOption> selectionOptions = new ArrayList<>();

    /**
     * Loads in a product based on existing data from a CSV file.
     * @param ID UID
     */
    public ProductVariant(String ID) {
        super(ID);
        setSerializationChain();
    }

    /**
     * Builds a new product variant during runtime.
     *
     * @param variantName       the variant name
     * @param variantCost       the variant cost
     * @param selectionRequired the selection required
     * @param selectionType     the selection type
     */
    public ProductVariant(String variantName, double variantCost, boolean selectionRequired,
                          String selectionType, List<ProductVariantOption> selectionOptions) {
        super(IDGenerator.generateGUID());
        this.variantName = variantName;
        this.variantCost = variantCost;
        this.selectionRequired = selectionRequired;
        this.selectionType = selectionType;
        this.selectionOptions = selectionOptions;
        setSerializationChain();
    }

    /**
     * Functions to call in order to safely modify the text file database.
     */
    @Override
    protected void setSerializationChain() {
//        this.serializationChain.add(super::getID);
        this.serializationChain.add(this::getVariantName);
        this.serializationChain.add(this::getVariantCost);
        this.serializationChain.add(this::isSelectionRequired);
        this.serializationChain.add(this::getSelectionType);
//        this.serializationChain.add(this::serializeOptions);
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
        super.setEntityName(variantName);
        super.hasChanged = true;
    }

    public double getVariantCost() {
        return variantCost;
    }

    public void setVariantCost(double variantCost) {
        this.variantCost = variantCost;
    }

    public boolean isSelectionRequired() {
        return selectionRequired;
    }

    public void setSelectionRequired(boolean selectionRequired) {
        this.selectionRequired = selectionRequired;
    }

    public String getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(String selectionType) {
        this.selectionType = selectionType;
    }

    public List<ProductVariantOption> getSelectionOptions() {
        return selectionOptions;
    }

    public void setSelectionOptions(List<ProductVariantOption> selectionOptions) { this.selectionOptions = selectionOptions; }

    public void addSelectionOption(ProductVariantOption variantOption) {
        if (variantOption.getVariant().getID().equals(super.getID())) {
            this.selectionOptions.add(variantOption);
            this.hasChanged = true;
        } else
            System.out.println(LogLevel.WARNING + "Unexpected error: variant option with ID '"
            + variantOption.getID() + "' parent is not set properly. Expected parent to be " + this.getVariantName()
            + " (" + super.getID() + "). Could not add variant option to this variant.");
    }
}
