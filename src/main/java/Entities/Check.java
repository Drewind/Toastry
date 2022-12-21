package Entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import Constants.TransactionOutcome;

/**
 * Represents a single transaction for a customer.
 */
public class Check extends Entity {
    private HashSet<CheckLineItem> checkLineItems = new HashSet<>();
    private Date purchaseDate = new Date();
    private int customersServed;

    private TransactionOutcome stage = TransactionOutcome.CREATED;
    private double subtotal;
    private double total;
    private double COGS;

    /**
     * Builds a new transaction through the application.
     */
    public Check() {
        super();
        setSerializationChain();
    }

    /**
     * Loads in a transaction based on existing data from a CSV file.
     * @param id {@code UID} unique identifier
     */
    public Check(final String id) {
        super(id);
        setSerializationChain();
    }

    /* ___________________________________________
                        GETTERS
    ___________________________________________ */
    /**
     * Retrieves the HashMap of all products purchased in this transaction.
     * Includes quantity of each product purchased.
     * @return HashMap<Product, Integer>
     */
    public final HashSet<CheckLineItem> getPurchases() {
        return this.checkLineItems;
    }

    public final String getPurchaseDateISO() {
        return this.purchaseDate.toInstant().toString();
    }

    /**
     * Retrieves how many customers were at this table during this transaction.
     * @return int
     */
    public final int getNumberServed() {
        return this.customersServed;
    }
    
    /**
     * Retrieves which stage this transaction is currently at.
     * @return {@code TransactionOutcome} stage of this transaction
     */
    public TransactionOutcome getStage() {
        return this.stage;
    }

    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Converts the purchase items and quantities into a single string representation
     * to be used in the database.
     * @return {@code String} resulting serialization
     */
    public String serializePurchases() {
        StringBuilder result = new StringBuilder();

        if (!this.checkLineItems.isEmpty()) {
            int i = 0;
            for (CheckLineItem entry : this.checkLineItems) {
                result
                        .append(entry.getProduct().getID())
                        .append("=")
                        .append(entry.getQuantity())
                        .append((++i != this.checkLineItems.size() ? "|" : ""));
            }
        }

        result.insert(0, "[").append("]");
        return result.toString(); // Remove the last pipe character
    }

    /* ___________________________________________
                        SETTERS
    ___________________________________________ */

    public void setProductsPurchased(Set<CheckLineItem> products) {
        this.checkLineItems = (HashSet<CheckLineItem>) products;
    }

    /**
     * Called during load ops on program startup.
     * @param cogs {@code double}
     */
    public void setCOGS(double cogs) {
        this.COGS = cogs;
    }

    /**
     * Called during load ops on program startup.
     * @param total {@code double}
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Called during load ops on program startup.
     * @param subtotal {@code double}
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Assigns the number of customers served from this transaction.
     * Made public so that other services can modify an existing order.
     * Called during load ops on program startup.
     * @param numOfCustomers {@code int}
     */
    public void setCustomersServed(int numOfCustomers) {
        this.customersServed = numOfCustomers;
    }

    /**
     * Assigns the purchase date of this transaction.
     * Made public so that other services can modify an existing order.
     * Called during load ops on program startup.
     * @param date {@code LocalDateTime}
     */
    public void setPurchaseDate(Date date) {
        this.purchaseDate = date;
    }

    /**
     * Adds a new entry into the {@code HashMap<Product, Int>} collection. Accepts a single product
     * with its purchased quantity.
     */
    public void addLineItem(final CheckLineItem checkLineItem) {
        this.checkLineItems.add(checkLineItem);
    }

    /**
     * Functions to call in order to safely modify the text file database.
     */
    @Override
    protected void setSerializationChain() {
        this.serializationChain.add(super::getID);
        this.serializationChain.add(this::getPurchaseDateISO);
        this.serializationChain.add(this::getNumberServed);
        this.serializationChain.add(this::getSubtotal);
        this.serializationChain.add(this::getTotal);
        this.serializationChain.add(this::getCOGS);
        this.serializationChain.add(this::serializePurchases);
    }

    /**
     * A simple method to complete the transaction by assigning a stage value of either
     * {@code COMPLETED_SUCCESSFULLY} or {@code COMPLETED_WITH_ERRORS}.
     * @param successful {@code boolean} if any errors were encountered while processing the transaction.
     */
    public void completeTransaction(final boolean successful) {
        this.stage = (successful ?
                TransactionOutcome.COMPLETED_SUCCESSFULLY:
                TransactionOutcome.COMPLETED_WITH_ERRORS
        );
    }

    public double getTotal() {
        return total;
    }

    public double getCOGS() {
        return COGS;
    }
}