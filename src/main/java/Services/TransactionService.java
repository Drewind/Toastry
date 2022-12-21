package Services;

import Constants.LogLevel;
import Entities.CheckLineItem;
import Entities.Product;
import Entities.Check;
import Interfaces.UndoInterface;
import Models.EntityModel;
import Models.ProductModel;

import java.util.HashSet;

/**
 * Service to be injected into views/controllers.
 * Handles processing transactions and purchases.
 */
public class TransactionService {
    private final EntityModel<Product> productModel;
    private final EntityModel<Check> transactionModel;
    
    public TransactionService(EntityModel<Product> productModel, EntityModel<Check> transactionModel) {
        this.productModel = productModel;
        this.transactionModel = transactionModel;
    }

    /**
     * Accepts a single transaction and processes it through the system.
     * First step is to update the product's performance variables (total sales, total revenue, total COGS)
     * by calling {@code addSale} on the product. With the product updated, we can register those updates with
     * {@code ProductModel}. Once all the products on the transaction has been processed, we will then record
     * the transaction itself using the {@code TransactionModel}.
     *
     * @return boolean if false, an error occurred.
     */
    public boolean processTransaction(Check check) {
        if (check.getPurchases() == null || check.getPurchases().isEmpty())
            return false;

        check.getPurchases().forEach((lineItem) -> {
            UndoInterface originalLineItem = new UndoTransaction(lineItem);
            Product product = lineItem.getProduct();

            product.setTotalSales(product.getTotalSales() + lineItem.getQuantity());
            product.setTotalExpenses(product.getTotalExpenses() + (product.getCost() * lineItem.getQuantity()));
            product.setTotalRevenue(product.getTotalRevenue() + (product.getPrice() * lineItem.getQuantity()));

            try {
                this.productModel.upsert(product);
                check.completeTransaction(true);
            } catch(Exception ex) {
                HashSet<CheckLineItem> lineItems = check.getPurchases();
                System.out.println(LogLevel.WARNING + "Could not process transaction! Rolling back product changes.");
                lineItems.remove(lineItem);
                lineItems.add((CheckLineItem) originalLineItem.undo());

                check.setProductsPurchased(lineItems);
                check.completeTransaction(false);
            }
        });

        try {
            this.transactionModel.insert(check);
        } catch (Exception e) {
            System.out.println(LogLevel.FATAL + "Could not modify transaction model! Reverting product changes.");
        }

        return true;
    }
}
