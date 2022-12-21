package Entities;

/**
 * Restaurant
 * An entity for a single restaurant. Defines variables and methods for a restaurant.
 */
public class Restaurant extends Entity{
    private double rent;
    private String name;
    private RentPeriod rentPeriod;

    /**
     * Builds a new restaurant entity through the application.
     * @param name string
     * @param rent double
     * @param rentPeriod RentPeriod
     */
    public Restaurant(String name, double rent, RentPeriod rentPeriod) {
        super();
        this.name = name;
        this.rent = rent;
        this.rentPeriod = rentPeriod;

        setSerializationChain();
    }

    /**
     * Loads in a restaurant based on existing data from a CSV file.
     * @param UID int; unique identifier for this entity
     */
    public Restaurant(String UID) {
        super(UID);
        setSerializationChain();
    }

    /* ___________________________________________
                        GETTERS
    ___________________________________________ */
    public double getRent() {
        return this.rent;
    }

    /**
     * Returns the name of this restaurant.
     * @return String
     */
    public String getRestaurantName() {
        return this.name;
    }

    /* ___________________________________________
                        SETTERS
    ___________________________________________ */

    /**
     * Functions to call in order to safely modify the text file database.
     */
    @Override
    protected void setSerializationChain() {
        this.serializationChain.add(this::getRestaurantName);
        this.serializationChain.add(this::getRent);
        this.serializationChain.add(this::getRentPeriod);
    }

    public void setRestaurantName(String name) {
        this.name = name;
    }

    public RentPeriod getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(RentPeriod rentPeriod) {
        this.rentPeriod = rentPeriod;
    }
}