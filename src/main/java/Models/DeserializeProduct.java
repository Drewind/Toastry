package Models;

import Entities.Product;
import Entities.ProductCategory;
import Entities.ProductVariant;
import Interfaces.DeserializeEntityInterface;
import Services.CSVParser;
import Services.VariantService;
import Utilities.LogService;

import java.util.List;

/**
 * A simple command to initialize an entity from the database based on its db content.
 * Based on a functional interface, allows for easy access by entity models to deserialize and load
 * entities.
 */
public class DeserializeProduct implements DeserializeEntityInterface {
    private CSVParser parser;
    private final LogService logger = new LogService(this.getClass().getName());
    private final VariantService variantService;

    public DeserializeProduct(final VariantService variantService) {
        this.variantService = variantService;
    }

    @Override
    public Product deserialize(final String str) {
        this.parser = new CSVParser(logger, str);
        Product product = new Product(parser.parseString(0));
        deserializeFields(product);
        product.resetChangedState();

        return product;
    }

    @Override
    public List<LogMessage> getLogs() {
        return this.logger.getLogs();
    }

    private void deserializeFields(final Product product) {
        product.setName(parser.parseStringOrDefault(1, "null"));
        product.setPrice(parser.parseCurrency(2));
        product.setCost(parser.parseCurrency(3));
        product.loadTotalSales(parser.parseInteger(4));
        product.setCategory(parseCategory(parser.parseString(5)));
        product.setVariants(variantService.getVariantsTiedToProduct(parser.parseListString(6)));
    }

    private ProductCategory parseCategory(final String categoryName) {
        return ProductCategory.getEnum(categoryName);
    }
}
