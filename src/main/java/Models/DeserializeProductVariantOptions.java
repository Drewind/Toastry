package Models;

import Entities.ProductVariant;
import Entities.ProductVariantOption;
import Interfaces.DeserializeEntityInterface;
import Services.CSVParser;
import Utilities.LogService;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * A simple command to initialize an entity from the database based on its db content.
 * Based on a functional interface, allows for easy access by entity models to deserialize and load
 * entities.
 */
public class DeserializeProductVariantOptions implements DeserializeEntityInterface {
    private CSVParser parser;
    private final LogService logger = new LogService(this.getClass().getName());
    private final ProductVariantModel variantModel;

    public DeserializeProductVariantOptions(final ProductVariantModel variantModel) { this.variantModel = variantModel; }

    @Override
    public ProductVariantOption deserialize(final String str) {
        this.parser = new CSVParser(logger, str);
        ProductVariantOption variantOption = new ProductVariantOption(parser.parseString(0));
        deserializeFields(variantOption);
        variantOption.resetChangedState();

        return variantOption;
    }

    @Override
    public List<LogMessage> getLogs() {
        return this.logger.getLogs();
    }

    private void deserializeFields(final ProductVariantOption variantOption) {
        Optional<ProductVariant> parentVariant = this.variantModel.findEntity(
                parser.parseString(1)
        );

        if (parentVariant.isPresent()) {
            variantOption.setParentVariant(parentVariant.get());
            parentVariant.get().addSelectionOption(variantOption);
        } else
            logger.logWarning("When deserializing option " + variantOption.getID() + ", the parent variant "
            + parser.parseString(1) + " is null and therefor cannot be tied to variant.");
        variantOption.setOptionName(parser.parseString(2));
    }
}
