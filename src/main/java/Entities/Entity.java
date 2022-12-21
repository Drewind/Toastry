package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import Services.IDGenerator;

/**
 * Standard template for entities in the database. All entities have a
 * final string ID. By default, a new UID will be generated but can be
 * supplied with an existing ID. Two list collections hold the necessary
 * sequence for loading and saving whatever entity subclass inherits entity.
 * 
 * Use the following code to add a new function in the save sequence:
 * super.CSV_SAVE_ORDER.add(this::FUNCTION_NAME);
 * 
 * CSV_SAVE_ORDER is a {@code List<Supplier<Object>>} containing operations to save data.
 * CSV_LOAD_ORDER is a {@code List<Consumer<Object>>} containing operations to load data.
 */
public class Entity implements Interfaces.Entity {
    protected final List<Supplier<Object>> serializationChain = new ArrayList<>();
    private final String ID;
    private String originalData;
    private String entityName;
    protected boolean hasChanged = false;
    private boolean isActive = false;


    /**
     * Loads in an existing UID.
     */
    public Entity(final String ID) {
        this.ID = ID;
        this.serializationChain.add(this::getID);
    }

    /**
     * Creates a new entity with a unique identifier.
     */
    public Entity() {
        this.ID = IDGenerator.generateGUID();
        this.originalData = "";
//        this.serializationChain.add(this::getID);
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(final String name) {
        this.entityName = name;
    }

    /**
     * Returns if the entity is soft-deleted.
     * @return {@code boolean}
     */
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


    /**
     * Returns a string to be used in a CSV file. Calls the entity's persist order chain
     * and joins each result with a comma to arrive at a final string returned.
     * @return {@code String} CSV line
     */
    public final String serialize() {
        return this.getSerializationChain()
                .stream().map(fun -> fun.get().toString())
                .collect(Collectors.joining(","));
    }


    public final String getOriginalData() {
        return this.originalData;
    }

    public void setOriginalData(final String line) {
        this.originalData = line;
    }

    public final String getID() {
        return this.ID;
    }

    /**
     * Returns whether the entity has been modified and not yet persisted.
     * A value of {@code true} indicates that changes to this entity has not been persisted yet.
     * @return {@code boolean}
     */
    public boolean hasChanged() {
        return this.hasChanged;
    }

    /**
     * Will reset this entity's hasChanged state; indicating this entity has not been modified and all changes are
     * saved.
     */
    public void resetChangedState() {
        this.hasChanged = false;
    }
    

    /**
     * Ordered list of functions to call to safely modify the text file database.
     * By default, this will include a single operation to persist the UID,
     * though entities inheriting from this class will enhance this array.
     * @return List<Supplier<Object>>
     */
    public List<Supplier<Object>> getSerializationChain() {
        return this.serializationChain;
    }


    /**
     * Functions to call in order to safely modify the text file database. Entities inheriting from this class
     * will define the chain of method calls to make in order to form the CSV data line.
     *
     * By default, just contains a singular method: get entity's ID.
     */
    protected void setSerializationChain() {}
}
