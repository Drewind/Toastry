package Models;

import Entities.Entity;
import Entities.ProductVariant;
import Entities.ProductVariantOption;
import Interfaces.DeserializeEntityInterface;
import Interfaces.ViewActionObserver;
import Utilities.LogService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <h2>Handles interactions with the database.</h2>
 *
 * <p>
 * Subclasses will inherit this base class and override the {@code ModelCommand} functions so
 * that they can implement their own base logic if necessary. If these {@code ModelCommand} functions
 * are not overridden, by default, it will use the standard save and load logic. These default
 * functions are intended for simple record interactions; persisting a entity without any
 * dependency on another entity or model.
 * </p>
 */
public abstract class EntityModel<T extends Entity> {
    private final ArrayList<ViewActionObserver> observers = new ArrayList<>();
    protected HashMap<String, T> entities = new HashMap<>();
    protected Path filePath;
    protected LogService logger;
    private int failedToParseEntities = 0;

    /**
     * Returns the number of entities that failed to load in.
     * @return {@code int}
     */
    public final int getFailedToParseCount() {
        return failedToParseEntities;
    }

    /**
     * Loads in all entities.
     */
    abstract public void loadEntities();

    public final Optional<T> findEntity(final T entity) {
        return Optional.ofNullable(entities.get(entity.getID()));
    }

    public final Optional<T> findEntity(final String entityID) {
        return Optional.ofNullable(entities.get(entityID));
    }

    public final Optional<T> findEntity(final int orderedEntityIndex) {
        List<T> entityList = this.entities.values().stream().toList();
        return Optional.ofNullable(entityList.get(orderedEntityIndex));
    }

    public final T retrieve(final String entityID) {
        return entities.get(entityID);
    }

    public final T retrieve(final int orderedEntityIndex) {
        List<T> entityList = this.entities.values().stream().toList();
        return entityList.get(orderedEntityIndex);
    }

    /**
     * Checks to see if the given UID for an entity in this model exists. Returns true if found.
     * @param UID unique ID of the entity
     * @return boolean, true if found
     */
    public final boolean entityExists(final String UID) {
        return entities.containsKey(UID);
    }

    /**
     * Checks to see if the entity in this model exists. Returns true if found.
     * @param entity is the subclass of type {@code Entity} being searched for
     * @return boolean, true if found
     */
    public final boolean entityExists(final T entity) {
        return entities.containsValue(entity);
    }

//    abstract public Entity getEntity(final String UID);

    public final List<T> getEntities() {
        return entities.values().stream().toList();
    }

    public void upsert(final T entity) {
        processUpsert(entity);
    }

    public void insert(final T entity) {
        processInsert(entity);
    }

    protected final void processUpsert(final T entity) {
        OptionalInt dbIndex = getIndexFromList(entity.getID(), readCSVFile(filePath));
        logger.logVerbose(dbIndex.isPresent() ?
                "Entity exists in database; updating record." : "Entity does not exist; inserting into database."
        );

        if (persistEntity(entity, filePath, dbIndex)) {
            entity.resetChangedState();
            entities.put(entity.getID(), entity);
            logger.logSuccess("Successfully upsert " + entity.getID() + " into database.");
        }
    }

    protected final void processInsert(final T entity) {
        OptionalInt dbIndex = getIndexFromList(entity.getID(), readCSVFile(filePath));
        if (dbIndex.isPresent()) {
            logger.logWarning("Entity " + entity.getID() + " exists in database and therefor cannot be added.");
            return;
        }

        if (persistEntity(entity, filePath, dbIndex)) {
            entity.resetChangedState();
            entities.put(entity.getID(), entity);
            logger.logSuccess("Successfully upsert " + entity.getID() + " into database.");
        }
    }

    protected final void processDelete(final T entity) {
        if (!this.entities.containsKey(entity.getID())) {
            logger.logWarning("Entity " + entity.getID() + " does not exists in model and therefor cannot be deleted.");
            return;
        }

        try {
            List<String> dbContent = Files.readAllLines(this.filePath).stream()
                    .filter(line -> !line.contains(entity.getID()))
                    .toList();

            Files.write(this.filePath, dbContent, StandardCharsets.UTF_8);
            this.entities.remove(entity.getID(), entity);
        } catch (Exception ex) {
            logger.logWarning("Encountered an exception (" + ex.getMessage() + ") in model " +
                    "while attempting to persist entity " + entity.getID() + ".\n" + ex);
        }
    }

    /**
     * Removes the requested entity from the database using a stream and filter.
     * @param entity {@code Entity} to be deleted.
     */
    abstract public void delete(T entity);

    /**
     * Registers a new observer for this model.
     * @param observer {@code observer} a entity that implements ViewActionObserver interface.
     */
    public final void registerObserver(ViewActionObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Removes an observer from the model.
     * @param observer {@code observer} a entity that implements ViewActionObserver interface.
     */
    public final void removeObserver(ViewActionObserver observer) {
        this.observers.remove(observer);
    }

    public final LogService getLogger() { return this.logger; }

    /**
     * Allows service classes whose job is to serialize and deserialize entities to increment this
     * model's failed count.
     */
    public final void incrementParserFail() { this.failedToParseEntities++; }

    public final int getFailedEntities() { return this.failedToParseEntities; }

    protected final List<String> readCSVFile(final Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            logger.logWarning("Could not read file path \n'" + path + "' in entity model; file was corrupt.");
            return List.of();
        }
    }

    protected final OptionalInt getIndexFromList(final String UID, final List<String> content) {
        for (int i = 0; i < content.size(); i++)
            if (content.get(i).contains(UID))
                return OptionalInt.of(i);
        return OptionalInt.empty();
    }

    private boolean persistEntity(final Entity entity, final Path path, final OptionalInt dbIndex) {
        try {
            List<String> dbContent = readCSVFile(path);

            String newLine = entity.getSerializationChain().stream().map(fun -> fun.get().toString()).collect(Collectors.joining(","));
            if (dbIndex.isPresent())
                dbContent.set(dbIndex.getAsInt(), newLine);
            else
                dbContent.add(newLine);

            entity.resetChangedState();
            Files.write(path, dbContent, StandardCharsets.UTF_8);
            return true;
        } catch (IOException ex) {
            logger.logWarning("Encountered an exception (" + ex.getMessage() + ") in model " +
                            "while attempting to persist entity " + entity.getID() + ".\n" + ex);
            return false;
        }
    }

//    public void reloadEntity(ProductVariant variant) {
//        DeserializeEntityInterface deserializeVariants = new DeserializeProductVariants(this);
//        variant = (ProductVariant) deserializeVariants.deserialize(variant.getOriginalData());
//
//        this.logger.logVerbose("Product variant model has finished loading entities." +
//                "\n\t• " + (this.variants.size() + this.variants.size()) + " successfully parsed." +
//                "\n\t• " + super.getFailedEntities() + " failed to parse.");
//
//        this.logger.addManyLogs(deserializeVariants.getLogs().toArray(new LogMessage[0]));
//    }
}
