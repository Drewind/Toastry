import Entities.Product;
import Entities.ProductVariant;
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
 *
 * <br><br>
 * Most tests will include a serialization and deserialization step to test both
 * persisting and retrieving the data.
 */
public class ProductModelTest {
    private final VariantService variantService = new VariantService();
    private final ProductModel productModel = new ProductModel(variantService);
    private final ProductVariantModel variantModel = new ProductVariantModel(variantService);
    private final ProductVariantOptionModel variantOptionModel = new ProductVariantOptionModel(variantService);


    /**
     * Initializes the test suite prior to running each test.
     * Populates the model by loading in entities from the DB.
     */
    @Before
    public void initialize() {
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
     * Confirms we can load products correctly stored in the DB.
     */
    @Test
    public void loadProducts() {
        ArrayList<Product> entities = new ArrayList<>(productModel.getEntities());
        assertEquals(0, productModel.getFailedToParseCount());

        System.out.println(" >> Returned " + entities.size() + " entities.");
        assertNotNull(entities);
    }


    /**
     * Confirms the ability to modify entities and persist those changes.
     *
     * Starts by obtaining a random entity in the DB and changes its name.
     * Then attempts to persist that change.
     */
    @Test
    public void upsertEntity() {
        Product entity = selectRandomEntity();
        String oldProductName = entity.getProductName();
        double oldPrice = entity.getPrice();

        entity.setName(MockService.randomizeProductName());
        entity.setPrice(MockService.randomDouble());
        entity.setCost(MockService.randomDouble());
        assertTrue(entity.hasChanged());

        productModel.upsert(entity);
        productModel.loadEntities();

        assertNotEquals(oldProductName, entity.getProductName());
        assertNotEquals(oldPrice, entity.getPrice());
        assertFalse(entity.hasChanged());
    }

    /**
     * Test should fail when modifying a product that does not exist. Validated by inspecting the logs and method
     * under test.
     */
    @Test
    public void upsertFailsWhenEntityIDIsNull() {
        Product entity = new Product(MockService.randomString());
        entity.setName(MockService.randomizeProductName());

        assertThrowsExactly(NullPointerException.class, () -> productModel.upsert(entity));
        assertTrue(entity.hasChanged());

        assertTrue(productModel.getLogger()
                .filterLogsByVerbose("Entity does not exist; inserting into database")
                .stream().findAny().isPresent()
        );
    }

    /**
     * When persisting a product to the database who have an invalid serialization step (i.e. in this case the product
     * variant does not exist), we exist product model to return false, indicating a failure.
     */
    @Test
    public void upsertFailsWhenEntitySerializationFails() {
        Product entity = selectRandomEntity();
        entity.setVariants(List.of(
                new ProductVariant(MockService.randomString())
        ));
        productModel.upsert(entity);

        assertTrue(productModel.getLogger()
                .filterLogsByWarning("not all variants are valid on the product object.")
                .stream().findAny().isPresent()
        );
    }


    /**
     * Confirms the ability to delete entities from the DB.
     * Selects a random entity and calls deleteEntity from the model.
     */
    @Test
    public void deleteEntity() {
        List<Product> oldEntities = productModel.getEntities();
        Product entity = selectRandomEntity();
        String entityID = entity.getID();

        productModel.delete(entity);
        productModel.loadEntities();

        assertFalse(productModel.entityExists(entityID));
        assertNotEquals(oldEntities, this.productModel.getEntities());
    }

    /**
     * Confirms ability to add a new entity to the DB.
     * Because the model utilizes the save order to properly
     * save an entity to the DB, we create a dummy product here.
     */
    @Test
    public void insertEntity() {
        Product entity = MockService.randomizeProduct();
        productModel.insert(entity);
        productModel.loadEntities();

        assertEquals(0, this.productModel.getFailedToParseCount());
        assertTrue(productModel.entityExists(entity.getID()));
    }

    /**
     * Test surrounding a use case when the entity already exists. Shouldn't add the product again and instead
     * should return false.
     */
    @Test
    public void insertEntityShouldFailIfProductExistAlready() {
        Product entity = selectRandomEntity();

        productModel.insert(entity);
        assertFalse(entity.hasChanged());

        assertTrue(productModel.getLogger()
                .filterLogsByWarning("exists in database")
                .stream().findAny().isPresent()
        );
    }


    /**
     * Full-fledged integration test for the model.
     * Confirms deletion, modification, and addition entities.
     */
    @Test
    public void integrationTest() {
        deleteEntity();

        upsertEntity();

        insertEntity();
    }


    /**
     * Obtains a random entity from the collection.
     * @return Entity
     * @see <a href="https://stackoverflow.com/questions/21092086/get-random-element-from-collection">Stackoverflow answer</a>
     */
    private Product selectRandomEntity() {
        Optional<Product> entity = productModel.getEntities().stream()
            .skip((int) (productModel.getEntities().size() * Math.random()))
            .findFirst();
        return (entity.orElse(null));
    }
}