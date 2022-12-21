package Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Product
 * An entity for a single product. Defines variables and methods for a product.
 */
public class Product extends Entity {
    private String name;
    private Double price; // Current price listed
    private double cost; // Current cost to make
    private ProductCategory category; // Assigned category
    private final ArrayList<ProductVariant> variants = new ArrayList<>();

    // @TODO move this to a `History` data class
    // See https://toastry.atlassian.net/browse/TOAST-13 for the story
    private int totalSales = 0;
    private double totalExpenses = 0.0;
    private double totalGrossSales = 0.0;
    private int dailySales = 0;

    /**
     * Builds a new product listing through the application.
     * @param name string
     * @param cost double; cost us to make
     * @param price double; price to buy from us
     * @param category ProductCategory
     */
    public Product(String name, double cost, double price, ProductCategory category) {
        super();
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.category = category;

        setSerializationChain();
    }

    /**
     * Loads in a product based on existing data from a CSV file.
     * @param id UID
     */
    public Product(final String id) {
        super(id);
        setSerializationChain();
    }

    /**
     * Functions to call in order to safely modify the text file database.
     */
    @Override
    protected void setSerializationChain() {
        this.serializationChain.add(super::getID);
        this.serializationChain.add(this::getProductName);
        this.serializationChain.add(this::getPrice);
        this.serializationChain.add(this::getCost);
        this.serializationChain.add(this::getTotalSales);
        this.serializationChain.add(this::categoryName);
        this.serializationChain.add(this::serializeVariants);
    }

    /**
     * Returns this product's assigned category.
     * @return ProductCategory
     */
    public ProductCategory getCategory() {
        return this.category;
    }

    public String categoryName() {
        return this.category.name();
    }

    /**
     * getPrice
     * Returns price for this product.
     * @return double price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * getCost
     * Returns cost to make this product.
     * @return double cost
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * getProductName
     * Returns the product/dish name.
     * @return String product/dish
     */
    public String getProductName() {
        return this.name;
    }

    /**
     * Returns this product's total sales over its life.
     * @return int
     */
    public int getTotalSales() {
        return this.totalSales;
    }

    /**
     * getTotalProfit
     * Retrieves the total profit for this product.
     * @return double
     */
    public double getTotalProfit() {
        return (this.price * this.totalSales) - (this.cost * this.totalSales);
    }

    /**
     * Retrieves total revenue for this product's lifespan.
     * @return double
     */
    public double getTotalRevenue() {
        return this.totalGrossSales;
    }

    /**
     * Retrieves total expenses for this product's lifespan.
     * @return double
     */
    public double getTotalExpenses() {
        return this.totalExpenses;
    }

    /**
     * Called internally to load total sales from the DB to the entity.
     * @param totalSales total sales
     */
    public void loadTotalSales(final int totalSales) {
        this.totalSales = totalSales;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }

    public boolean removeVariant(ProductVariant variant) {
        return this.variants.removeIf(v -> v.getID().equals(variant.getID()));
    }

    /**
     * Converts the purchase items and quantities into a single string representation
     * to be used in the database.
     * @return {@code String} resulting serialization
     */
    private String serializeVariants() {
        StringBuilder result = new StringBuilder();
        int i = 0;

        for (ProductVariant entry : this.variants) {
            result.append(entry.getID()).append((++i != this.variants.size() ? "|" : ""));
        }

        result.insert(0, "[").append("]");
        return result.toString();
    }

    /**
     * Assigns the product's name. Used by methods to change this product's name.
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
        this.hasChanged = true;
    }

    /**
     * Assigns a cost to this product.
     * todo add this to the change history table
     * @param cost double
     */
    public void setCost(double cost) {
        this.cost = cost;
        this.hasChanged = true;
    }

    /**
     * Assigns the price of this product.
     * todo add this to the change history table
     * @param price double
     */
    public void setPrice(double price) {
        this.price = price;
        this.hasChanged = true;
    }

    /**
     * Assigns the category to this product.
     * todo add this to the change history table
     * @param category ProductCategory
     */
    public void setCategory(ProductCategory category) {
        this.category = category;
        this.hasChanged = true;
    }

    /**
     * Used by the deserializer to initially set a list of variants assigned to this product.
     * Will clear the variants and then perform an {@code addAll} operation given a {@code List<ProductVariant>}.
     * @param variants {@code List<ProductVariant>} variants to set.
     */
    public void setVariants(List<ProductVariant> variants) {
        this.variants.clear();
        this.variants.addAll(variants);
    }

    public void addVariant(ProductVariant variant) {
        this.variants.add(variant);
    }

    /**
     * Checks to see if the requested {@code ProductVariant} exists in this product's variant list.
     * @param variant {@code ProductVariant} to check for.
     * @return {@code boolean} true if found and false if doesn't exist.
     */
    public boolean variantExists(ProductVariant variant) {
        return this.variants.contains(variant);
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void setTotalRevenue(double totalGrossSales) {
        this.totalGrossSales = totalGrossSales;
    }
}