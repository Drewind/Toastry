package TestSuite;

import Constants.LogLevel;
import Entities.Check;
import Entities.CheckLineItem;
import Entities.Product;
import Entities.ProductVariant;
import Entities.ProductVariantSelected;
import Models.ProductModel;
import Models.TransactionModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Part of the unit testing framework. Used to generate a CSV file of
 * mock data for various models and entities.
 */
public class CSVTestService {
    private final ProductModel productModel;

    public CSVTestService(ProductModel productModel) { this.productModel = productModel; }

    /**
     * Generates a new transaction CSV file containing mock data. Takes in a string
     * representing the path to the desired file and an integer with the number of
     * records to generate.
     *
     * Requires a {@code ProductModel} passed into this class's constructor as this
     * method will tie transactions to products drawn from the product model.
     * @param csvPath {@code String} path to the file.
     * @param recordsToGenerate {@code int} records to generate.
     */
    public TransactionModel mockTransactions(final String csvPath, final int recordsToGenerate) {
        List<String> lines = serializeTransactions(recordsToGenerate);
        generateFileContents(csvPath, lines);
        return new TransactionModel(null);
    }

    /**
     * Returns a fully built transaction for testing purposes. Inserts a random number of
     * purchased products and table size.
     * @return {@code Transaction} random transaction
     */
    public Check randomizeTransaction() {
        HashSet<CheckLineItem> purchasedProducts = new HashSet<>();
        int randomProductCount = MockService.randomInt();
        while (purchasedProducts.size() < randomProductCount) {
            purchasedProducts.add(new CheckLineItem(
                    randomProduct(),
                    new ProductVariantSelected(
                            randomProduct()
                    )
            ));
        }

        return new Check();
    }

//______________________________________________________________________________________________________________________
    private List<String> serializeTransactions(final int recordsToGenerate) {
        List<String> transactions = new ArrayList<>();
        for (int i = 0; i < recordsToGenerate; i++) {
            transactions.add(randomizeTransaction().serialize());
        }
        return transactions;
    }

    private void generateFileContents(final String csvPath, final List<String> data) {
        try {
            Files.write(Paths.get(csvPath), data, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(LogLevel.FATAL + "Could not save file.");
        }
    }

    /**
     * Returns a random {@code Product} mainly for testing purposes.
     * @return {@code Product}
     */
    public Product randomProduct() {
        List<Product> products = this.productModel.getEntities();
        return products.get(MockService.randomInt(0, products.size()));
    }
}
