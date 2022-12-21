import Entities.Product;
import Entities.ProductVariant;
import Entities.ProductVariantOption;
import Models.ProductModel;
import Models.ProductVariantModel;
import Models.ProductVariantOptionModel;
import Services.VariantService;
import TestSuite.MockService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

/**
 * Individual unit test against components and one
 * integration test to confirm functionality.
 */
public class VariantModelTest {
    private final VariantService variantService = new VariantService();
    private final ProductModel productModel = new ProductModel(variantService);
    private final ProductVariantModel variantModel = new ProductVariantModel(variantService);
    private final ProductVariantOptionModel variantOptionModel = new ProductVariantOptionModel(variantService);


    /**
     * Initializes the test suite prior to running each test.
     * Populates the model by loading in entities from the DB.
     */
    @Before
    public void initializeTest() {
        variantService.init(productModel, variantModel, variantOptionModel);
        variantModel.loadEntities();
        variantOptionModel.loadEntities();
        productModel.loadEntities();
    }


    /**
     * Saves the results of the running test.
     */
    @After
    public void saveTestResult() {
        String DEBUG_HEADER = "\n------------------------------------------";
        System.out.printf("%s\nLOGS\n", DEBUG_HEADER);
        productModel.getLogger().getLogs().forEach(System.out::println);
        System.out.println("\nSaved results to file.");
    }


    /**
     * Confirms we can load products correctly stored in the DB. Also validates the deserializers.
     */
    @Test
    public void loadVariants() {
        ArrayList<ProductVariant> entities = new ArrayList<>(variantModel.getEntities());

        assertFalse(variantModel.getLogger()
                .filterLogsByWarning("does not exist and therefor cannot be tied to the variant.")
                .stream().findAny().isPresent()
        );
        System.out.println(" >> Returned " + entities.size() + " variants.");
        assertNotNull(entities);
    }

    /**
     * Confirms the ability to modify entities and persist those changes.
     *
     * Starts by obtaining a random entity in the DB and changes its name.
     * Then attempts to persist that change.
     */
    @Test
    public void upsertVariantChanges() {
        ProductVariant entity = selectRandomVariant();
        String oldName = entity.getVariantName();

        entity.setVariantName(MockService.randomizeProductName());
        assertTrue(entity.hasChanged());

        variantModel.upsert(entity);
        variantModel.loadEntities();

        assertNotEquals(oldName, entity.getVariantName());
        assertFalse(entity.hasChanged());
    }

    /**
     * Test should fail when modifying a variant that does not exist. Validated by inspecting the logs and method
     * under test. We set a random name to the variant, proving that the model does not reset the `hasChanged` value.
     */
    @Test
    public void upsertFailsWhenVariantIDIsNull() {
        ProductVariant variant = new ProductVariant(MockService.randomString());
        variant.setVariantName(MockService.randomizeProductName());

        assertThrowsExactly(NullPointerException.class, () -> variantModel.upsert(variant));
        assertTrue(variant.hasChanged());
    }

    @Test
    public void upsertFailsWhenVariantSerializationFails() {
        ProductVariant entity = selectRandomVariant();
        entity.setSelectionOptions(List.of(
                new ProductVariantOption(MockService.randomString())
        ));
        variantModel.upsert(entity);

        assertTrue(variantModel.getLogger()
                .filterLogsByWarning("not all variant options are valid on the variant object.")
                .stream().findAny().isPresent()
        );
    }


    /**
     * Confirms the application can create a new variant option and add it to a variant.
     * Will reload the model after creating the variant to validate we can load it in correctly.
     */
    @Test
    public void insertNewVariantOption() {
        ProductVariant variant = MockService.randomizeVariant();
        ProductVariantOption variantOption = new ProductVariantOption(variant, MockService.randomizeProductName());
        variant.addSelectionOption(variantOption);
        assertTrue(variant.hasChanged());

        variantModel.insert(variant);
        variantOptionModel.insert(variantOption);
        variantModel.linkVariantOptionToVariant(variantOption, variant);

        variantModel.loadEntities();
        variantOptionModel.loadEntities();

        assertTrue(variantOptionModel.entityExists(variantOption.getID()));
        ProductVariant reloadedVariant = variantModel.retrieve(variant.getID());
        assertEquals(1,
            reloadedVariant.getSelectionOptions().stream().filter(option -> option.getID().equals(variantOption.getID())).count()
        );
    }


    /**
     * Confirms we can remove a variant option from a variant.
     */
    @Test
    public void deleteVariantOption() {
        ProductVariant variant = selectRandomVariant();
        ProductVariantOption variantOption = new ProductVariantOption(variant, MockService.randomizeProductName());
        variantOptionModel.insert(variantOption);
        variantModel.upsert(variant);
        variantModel.linkVariantOptionToVariant(variantOption, variant);

        variantOptionModel.delete(variantOption);
        variantModel.loadEntities();
        variantOptionModel.loadEntities();

        ProductVariant reloadedVariant = variantModel.retrieve(variant.getID());
        assertEquals(0,
                reloadedVariant.getSelectionOptions().stream().filter(option -> option.getID().equals(variantOption.getID())).count()
        );
        assertFalse(variantOptionModel.entityExists(variantOption.getID()));
    }

    /**
     * When a deleted call to a variant is called, we expect all variant options to be removed. This test
     * setups the correct test for that assertion: a new variant with two variant options assigned to it.
     */
    @Test
    public void deleteVariantWithVariantOptions() {
        ProductVariant variant = MockService.randomizeVariant();
        ProductVariantOption variantOption1 = new ProductVariantOption(variant, MockService.randomizeProductName());
        ProductVariantOption variantOption2 = new ProductVariantOption(variant, MockService.randomizeProductName());
        variantModel.insert(variant);
        variantOptionModel.insert(variantOption1);
        variantOptionModel.insert(variantOption2);
        variantModel.linkVariantOptionToVariant(variantOption1, variant);
        variantModel.linkVariantOptionToVariant(variantOption2, variant);

        variantModel.delete(variant);
        variantModel.loadEntities();
        variantOptionModel.loadEntities();

        assertFalse(variantModel.entityExists(variant));
        assertFalse(variantOptionModel.entityExists(variantOption1));
        assertFalse(variantOptionModel.entityExists(variantOption2));
        assertEquals(0, variantOptionModel.getLogger().filterLogsByWarning().size());
    }

    @Test
    public void deleteVariantTiedToProducts() {
        ProductVariant variant = MockService.randomizeVariant();
        Product product1 = MockService.randomizeProduct();
        Product product2 = MockService.randomizeProduct();
        variantModel.insert(variant);
        productModel.insert(product1);
        productModel.insert(product2);
        product1.addVariant(variant);
        product2.addVariant(variant);

        variantModel.delete(variant);
        variantModel.loadEntities();
        productModel.loadEntities();

        assertEquals(0, variantOptionModel.getLogger().filterLogsByWarning().size());
        Product reloadedProduct1 = productModel.retrieve(product1.getID());
        Product reloadedProduct2 = productModel.retrieve(product2.getID());
        assertEquals(0, reloadedProduct1.getVariants().size());
        assertEquals(0, reloadedProduct2.getVariants().size());
    }

    @Test
    public void deleteShouldFailIfVariantIsNull() {
        variantModel.delete(MockService.randomizeVariant());
        assertTrue(variantModel.getLogger()
                .filterLogsByWarning("does not exists")
                .stream().findAny().isPresent()
        );
    }


    /**
     * Confirms variants can be added to a product.
     */
    @Test
    public void addVariantsToProduct() {
        Product product = selectRandomProduct();
        ProductVariant variant = MockService.randomizeVariant();
        product.addVariant(variant);

        this.variantModel.insert(variant);
        this.productModel.upsert(product);
        this.variantModel.loadEntities();
        this.productModel.loadEntities();

        Product reloadedProduct = productModel.retrieve(product.getID());
        assertFalse(product.hasChanged());
        assertTrue(reloadedProduct.getVariants().stream().anyMatch(v -> v.getID().equals(variant.getID())));
    }

    @Test
    public void insertVariantShouldFailIfExistsAlready() {
        ProductVariant variant = selectRandomVariant();

        variantModel.insert(variant);
        assertTrue(variantModel.getLogger()
                .filterLogsByWarning("exists in database")
                .stream().findAny().isPresent()
        );
    }


    /**
     * Obtains a random entity from the collection.
     * @return Entity
     * @see <a href="https://stackoverflow.com/questions/21092086/get-random-element-from-collection">Stackoverflow answer</a>
     */
    private Product selectRandomProduct() {
        Optional<Product> entity = productModel.getEntities().stream()
                .skip((int) (productModel.getEntities().size() * Math.random()))
                .findFirst();
        return (entity.orElse(null));
    }


    /**
     * Obtains a random entity from the collection.
     * @return Entity
     * @see <a href="https://stackoverflow.com/questions/21092086/get-random-element-from-collection">Stackoverflow answer</a>
     */
    private ProductVariant selectRandomVariant() {
        Optional<ProductVariant> entity = variantModel.getEntities().stream()
                .skip((int) (variantModel.getEntities().size() * Math.random()))
                .findFirst();
        return (entity.orElse(null));
    }
}