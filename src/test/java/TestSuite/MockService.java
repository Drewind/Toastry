package TestSuite;

import Entities.*;
import Services.IDGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A static helper class to mock entities in the program.
 */
public class MockService {
    private final static String[] RANDOM_PREFIX_NAMES = {
            "Admiral's ", "Captain's ", "Party ", "Chef's ", "Devy Jone's ", "Castaway ",
            "Jalapeño ", "Sunken ", "Mango ", "Sea Salted ", "High-Tide ", "Privateers ",
            "Shipwreck ",
    };
    private final static String[] RANDOM_SUFFIX_NAMES = {
            "Soup", "Brownies", "Cheeseburger", "Bacon Sandwich", "Nuggets", "Lobster", "Shrimp",
            "Fried Chicken", "Crap leg", "Eel", "King Crab"
    };
    private final static String[] RANDOM_PRODUCT_NAMES = {
            "Dropp'n Anchor",
            "Sail Away",
            "Mysterious Droppings",
            "Triple Stack Flaming Cheese With Lightly Roasted Peppers",
            "Spanish Privateers",
            "The Junior Sailor",
            "Bottom of the Sea",
            "Hopeless Sailor",
            "There She Blows",
            "Today's Catch",
            "Bikini Float"
    };

    private final static String[] RANDOM_VARIANT_OPTIONS = {
            "Extra Spicy",
            "Mild",
            "On The Sauce",
            "Hot Hot Hot",
            "Rare",
            "Medium",
            "Well Done",
            "No Peanuts",
            "Tomatoes",
            "Dark Blend",
            "Light Blend",
            "Heart-Healthy",
    };

    /**
     * Returns a random name for products.
     * @return String
     */
    public static String randomizeProductName() {
        String name = "";

        // Use a predefined name or create a new name.
        if (ThreadLocalRandom.current().nextInt(0, 4) == 0) {
            name += RANDOM_PRODUCT_NAMES[ThreadLocalRandom.current().nextInt(0, RANDOM_PRODUCT_NAMES.length)];
        } else {
            name += RANDOM_PREFIX_NAMES[ThreadLocalRandom.current().nextInt(0, RANDOM_PREFIX_NAMES.length)]
                    += RANDOM_SUFFIX_NAMES[ThreadLocalRandom.current().nextInt(0, RANDOM_SUFFIX_NAMES.length)];
        }

        return name;
    }

    public static String randomizeVariantOption() {
        return RANDOM_VARIANT_OPTIONS[ThreadLocalRandom.current().nextInt(0, RANDOM_VARIANT_OPTIONS.length)];
    }

    public static String randomString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i <= 8; i++) {
            str.append(randomChar());
        }

        return str.toString();
    }

    private static char randomChar() {
        Random r = new Random();
        return (char)(r.nextInt('Z' - 'A') + 'a');
    }

    /**
     * Returns a random integer.
     * @return {@code int}
     */
    public static int randomInt() {
        Random r = new Random();
        return (r.nextInt('9' - '0') + '0');
    }

    public static int randomInt(int min, int max) {
        Random r = new Random();
        return (r.nextInt(max - min) + min);
    }

    public static double randomDouble() {
        Random r = new Random();
        return (r.nextDouble(999.99 - 0.01) + 0.01);
    }

    public static double randomDouble(double min, double max) {
        Random r = new Random();
        return (r.nextDouble(max - min) + min);
    }

    public static double randomCurrency() {
        Random r = new Random();

        BigDecimal bd = BigDecimal.valueOf(r.nextDouble());
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean randomBoolean() {
        Random r = new Random();
        return r.nextBoolean();
    }

    public static ProductCategory randomizeProductCategory() {
        ProductCategory[] categories = ProductCategory.values();
        int randomInt = (int) (Math.random() * ((categories.length - 1) + 1));
        return categories[randomInt];
    }

    public static Product randomizeProduct() {
        return new Product(IDGenerator.generateGUID()) {{
            setTotalRevenue(randomCurrency());
            setTotalExpenses(randomCurrency());
            setTotalSales(randomInt());
            setCategory(randomizeProductCategory());
            setName(randomizeProductName());
            setCost(randomCurrency());
            setPrice(randomCurrency());
            setOriginalData(serialize());
            setVariants(Arrays.asList(randomizeVariant()));
        }};
    }

    public static ProductVariantSelected randomizeProductVariantSelected(final Product product) {
        ProductVariant randomVariant = product.getVariants().get(MockService.randomInt(0, product.getVariants().size()));
        ProductVariantOption randomVariantOption = randomVariant.getSelectionOptions().get(MockService.randomInt(0, randomVariant.getSelectionOptions().size()));
        return new ProductVariantSelected(
                product,
                randomVariant,
                randomVariantOption
        );
    }

    public static ProductVariant randomizeVariant() {
        return new ProductVariant(IDGenerator.generateGUID()) {{
            setVariantName(randomString());
            setVariantCost(randomCurrency());
            setSelectionRequired(randomBoolean());
            setSelectionType(randomString());
            setSelectionOptions(Arrays.asList(randomizeVariantOption(this)));
        }};
    }

    private static ProductVariantOption randomizeVariantOption(final ProductVariant variant) {
        return new ProductVariantOption(
                variant,
                randomizeVariantOption()
        );
    }
}
