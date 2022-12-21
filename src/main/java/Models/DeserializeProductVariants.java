package Models;

import Entities.ProductVariant;
import Interfaces.DeserializeEntityInterface;
import Services.CSVParser;
import Utilities.LogService;

import java.util.List;

/**
 * A simple command to initialize an entity from the database based on its db content.
 * Based on a functional interface, allows for easy access by entity models to deserialize and load
 * entities.
 */
public class DeserializeProductVariants implements DeserializeEntityInterface {
    private CSVParser parser;
    private final LogService logger = new LogService(this.getClass().getName());

    @Override
    public ProductVariant deserialize(final String str) {
        this.parser = new CSVParser(logger, str);
        ProductVariant variant = new ProductVariant(parser.parseString(0));
        deserializeFields(variant);
        variant.resetChangedState();

        return variant;
    }

    @Override
    public List<LogMessage> getLogs() {
        return this.logger.getLogs();
    }

    private void deserializeFields(final ProductVariant variant) {
        variant.setVariantName(parser.parseStringOrDefault(1, "null"));
        variant.setVariantCost(parser.parseDouble(2));
        variant.setSelectionRequired(parser.parseBoolean(3));
        variant.setSelectionType(parser.parseString(4));
    }
}
