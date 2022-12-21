import Entities.*;
import Models.ProductModel;
import Models.TransactionModel;
import Services.VariantService;
import TestSuite.MockService;
import Services.TransactionService;
import TestSuite.CSVTestService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Confirms functionality of the ever so important TransactionService.
 */
public class CheckServiceTest {
    private final String DEBUG_HEADER = "\n------------------------------------------";
    private final VariantService variantService = new VariantService();
    private final ProductModel productModel = new ProductModel(variantService);
    private final CSVTestService csvService = new CSVTestService(productModel);
    private final TransactionModel transactionModel = csvService.mockTransactions("src/test/resources/MOCK_TRANSACTIONS.csv", 10);
    private final TransactionService transactionService = new TransactionService(productModel, null); // TODO important, replace second arg


    /**
     * Initializes the test suite prior to running each test.
     * Populates the model by loading in entities from the DB.
     */
    @Before
    public void initializeTest() {
//        variantService.init(productModel, null);
        productModel.loadEntities();
        transactionModel.loadEntities();
        System.out.println(DEBUG_HEADER);
    }


    /**
     * Saves the results of this test.
     */
    @After
    public void saveTestResult() {
//        productModel.saveReport("src/test/Results/"
//                + IDGenerator.generateGUID() + ".txt");
        System.out.println(DEBUG_HEADER);
        System.out.println("Saved results to file.");
    }


    /**
     * Confirms ability to load in entities.
     */
    @Test
    public void loadEntitiesTest() {
        System.out.println("\tLoad Entities From Model\n");

        ArrayList<Entity> entities = new ArrayList<>(productModel.getEntities());
        Entity randomEntity = entities.get(ThreadLocalRandom.current().nextInt(0, entities.size()));
        System.out.println(" >> Returned " + entities.size() + " entities.");
        System.out.println(" >> Random entity\n" + randomEntity.getID());

        assertNotNull(entities);
    }


    /**
     * Process a transaction successfully without any exceptions or errors.
     * First product should have fields modified.
     */
//    @Test
//    public void processTransactionSuccessfully() {
//        Check check = randomCheck();
//        Product firstProduct = check.getPurchases().stream().findFirst().orElseThrow(IllegalStateException::new).getProduct();
//        int oldTotalSales = firstProduct.getTotalSales();
//        double oldTotalExpenses = firstProduct.getTotalExpenses();
//        double oldTotalGrossSales = firstProduct.getTotalRevenue();
//
//        assertTrue(this.transactionService.processTransaction(check));
//        assertNotEquals(oldTotalSales, firstProduct.getTotalSales());
//        assertNotEquals(oldTotalExpenses, firstProduct.getTotalExpenses());
//        assertNotEquals(oldTotalGrossSales, firstProduct.getTotalRevenue());
//    }


    /**
     * We expect there to be at least one {@code TransactionLineItem} inside the transaction.
     * If this is not the case, it should return false and continue execution.
     */
    @Test
    public void shouldReturnFalseWhenPurchasesEmpty() {
        Check check = randomCheck();
        check.setProductsPurchased(null);

        assertFalse(this.transactionService.processTransaction(check));
    }

    @Test(expected = Exception.class)
    public void expectExceptionWhenLineItemProductNull() {
        Check check = randomCheck();
        HashSet<CheckLineItem> lineItems = new HashSet<>();
        lineItems.add(new CheckLineItem(
                MockService.randomizeProduct(),
                new ProductVariantSelected(MockService.randomizeProduct())
        ));
        lineItems.add(new CheckLineItem(
                null,
                null
        ));

        check.setProductsPurchased(lineItems);
        this.transactionService.processTransaction(check);
    }

    /**
     * Returns a random check entity from a list. Call the model's {@code getEntities} method to this method.
     * Then cast the return entity to the desired entity subclass.
     * @return {@code Entity} or {@code null}
     * @see <a href="https://stackoverflow.com/questions/21092086/get-random-element-from-collection">Stackoverflow answer</a>
     */
    private Check randomCheck() {
        Optional<Check> entity = this.transactionModel.getEntities().stream()
                .skip((int) (this.transactionModel.getEntities().size() * Math.random()))
                .findFirst();
        return (entity.orElse(null));
    }
}