package Models;

import Entities.ProductVariant;
import Entities.ProductVariantOption;
import Interfaces.DeserializeEntityInterface;
import Services.IDGenerator;
import Services.VariantService;
import Utilities.LogService;

import java.nio.file.Paths;
import java.util.Optional;

/**
 * A class to handle reading and writing of variant entities.
 */
public class ProductVariantModel extends EntityModel<ProductVariant> {
    private final VariantService variantService;

    public ProductVariantModel(VariantService variantService) {
        this.filePath = Paths.get("src/main/resources/ProductVariants.txt");
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
    public void upsert(ProductVariant entity) {
        if (variantService.validateVariantOptionsTiedToVariant(entity)) {
            super.processUpsert(entity);
        } else {
            logger.logWarning("Could not upsert entity " + entity.getID() + "; not all variant options "
            + "are valid on the variant object.");
        }
    }

    @Override
    public void delete(ProductVariant entity) {
        super.processDelete(entity);
        variantService.deleteVariantOptionsFromVariant(entity);
        variantService.deleteVariantFromProducts(entity);
    }

    /**
     * Loads all the entities in this model. Required to call prior to using the model.
     */
    @Override
    public void loadEntities() {
        DeserializeEntityInterface deserializeVariants = new DeserializeProductVariants();
        this.entities.clear();

        readCSVFile(this.filePath).stream()
                .filter(this::lineIsValid)
                .map(deserializeVariants::deserialize)
                .forEach(entity -> this.entities.put(entity.getID(), (ProductVariant) entity));

        this.logger.logVerbose("Product variant model has finished loading entities." +
                "\n\t• " + this.entities.size() + " successfully parsed." +
                "\n\t• " + super.getFailedEntities() + " failed to parse.");

        this.logger.addManyLogs(deserializeVariants.getLogs().toArray(new LogMessage[0]));
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

    public void linkVariantOptionToVariant(final ProductVariantOption variantOptionToBeLinked, final ProductVariant variantParent) {
        variantOptionToBeLinked.setParentVariant(variantParent);
        this.variantService.linkVariantOptionToVariant(variantParent, variantOptionToBeLinked);
    }

    public ProductVariantOptionModel getVariantOptionModel() {
        return this.variantService.variantOptionModel();
    }
}