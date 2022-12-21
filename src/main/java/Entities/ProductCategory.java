package Entities;

import Constants.LogLevel;

/**
 * A product may have one category assigned to it. By default, `UNASSIGNED` is given to a product.
 */
public enum ProductCategory {
    MAIN_DISH,
    SIDE_DISH,
    BEVERAGE,
    DESSERT,
    SOUP,
    UNASSIGNED;

    /**
     * Attempts to find the enum value based on its name. If a value does not exist, outputs a warning message and
     * returns `ProductCategory.UNASSIGNED`.
     * @param value {@code String} name of enum
     * @return {@code ProductCategory}
     */
    public static ProductCategory getEnum(String value) {
        for(ProductCategory v : values())
            if(v.name().equalsIgnoreCase(value)) return v;
        
        System.out.println(LogLevel.WARNING + "No ProductCategory was found for '" + value + "'. Returning UNASSIGNED.");
        return ProductCategory.UNASSIGNED;
    }
}
