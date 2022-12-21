package Models;

import Entities.ProductVariantOption;
import Interfaces.DeserializeEntityInterface;
import Services.IDGenerator;
import Services.VariantService;
import Utilities.LogService;

import java.nio.file.Paths;

/**
 * A class to handle reading and writing of variant entities.
 */
public class ProductVariantOptionModel extends EntityModel<ProductVariantOption> {
    private final VariantService variantService;

    public ProductVariantOptionModel(VariantService variantService) {
        this.filePath = Paths.get("src/main/resources/ProductVariantOptions.txt");
        this.logger = new LogService(this.getClass().getName());
        this.variantService = variantService;
    }

    /**
     * Persists a {@code Product} entity to the database.
     *
     * Performs validation to ensure variants stored in the product are valid instances. If a variant does not
     * exist at the time of writing to the database, that variant is skipped and removed from the entity, logging
     * an exception.
     * @param entity {@code Entity} to be modified.
     * @return {@code boolean} result of the operation; true for success and false otherwise.
     */
    @Override
    public void upsert(ProductVariantOption entity) {
        super.processUpsert(entity);
    }

    @Override
    public void delete(ProductVariantOption entity) {
        super.processDelete(entity);
    }

    /**
     * Loads all the entities in this model. Required to call prior to using the model.
     */
    @Override
    public void loadEntities() {
        DeserializeEntityInterface deserializeVariantOptions = new DeserializeProductVariantOptions(this.variantService.variantModel());
        this.entities.clear();

        readCSVFile(this.filePath).stream()
                .filter(this::lineIsValid)
                .map(deserializeVariantOptions::deserialize)
                .forEach(entity -> this.entities.put(entity.getID(), (ProductVariantOption) entity));

        this.logger.logVerbose("Product variant model has finished loading entities." +
                "\n\t• " + this.entities.size() + " successfully parsed." +
                "\n\t• " + super.getFailedEntities() + " failed to parse.");

        this.logger.addManyLogs(deserializeVariantOptions.getLogs().toArray(new LogMessage[0]));
    }

    /**
     * Performs a battery of tests against each line item to verify the line is valid. Ensures each line:
     * <ul>
     *      <li>contains at least one element,</li>
     *      <li>a valid GUID,</li>
     *      <li>and each field is not empty or blank</li>
     *  </ul>
     * @param str {@code String} DB line
     * @return {@code boolean} true if valid and false if corrupt
     */
    private boolean lineIsValid(final String str) {
        String[] fields = str.split(",");
        if (fields.length == 0) { incrementParserFail(); return false; }
        if (!IDGenerator.isValidGUID(fields[0])) { incrementParserFail(); return false; }

        for (String field : fields)
            if (field.isEmpty() || field.isBlank()) {
                incrementParserFail();
                return false;
            }

        return true;
    }
}