package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

import Constants.LogLevel;
import Entities.Restaurant;

/**
 * Handles reading and writing of restaurant location entities.
 * @TODO refactor this into the newer {@code EntityModel<>} format
 */
public class RestaurantModel extends Model {
    private final HashMap<String, Restaurant> entities = new HashMap<>();

    public RestaurantModel() {
        super("src/main/resources/LocationList.txt", 0); // todo refactor this
        loadEntities();
    }

    public RestaurantModel(final String CSV_PATH) {
        super(CSV_PATH, 0);
    }

    public Restaurant getLocation(final String UID) {
        return this.entities.get(UID);
    }

    public boolean addLocation(Restaurant entity) {
        try {
            if (this.entities.containsKey(entity.getID())) {
                super.addLogMessage(LogLevel.WARNING, "Location (" + entity.getID() + ") in model already exists.");
                return false;
            }

            super.addEntity(entity);
            this.entities.put(entity.getID(), entity);
            return true;
        } catch (Exception ex) {
            super.addLogMessage(LogLevel.WARNING, "An error occurred when persisting location (" + entity.getID() + ") in model.\n\t" + ex, true);
        }
        return false;
    }

    public boolean editLocation(Restaurant location) {
        try {
            if (this.entities.containsKey(location.getID())) {
                super.editEntity(location);
                this.entities.put(location.getID(), location);
                return true;
            }

            super.addLogMessage(LogLevel.WARNING, "Could not find location (" + location.getID() + ") in model.");
            return false;
        } catch (Exception ex) {
            super.addLogMessage(LogLevel.WARNING, "An error occurred when editing location (" + location.getID() + ") in model.\n\t" + ex, true);
        }
        return false;
    }

    public boolean deleteLocation(Restaurant location) {
        try {
            if (this.entities.containsKey(location.getID())) {
                super.deleteEntity(location);
                this.entities.remove(location.getID());
                return true;
            }

            super.addLogMessage(LogLevel.WARNING, "Could not find location (" + location.getID() + ") in model.", true);
            return false;
        } catch (Exception ex) {
            super.addLogMessage(LogLevel.WARNING, "An error occurred when deleting location (" + location.getID() + ") in model.\n\t" + ex, true);
        }
        return false;
    }

    /**
     * Loads all the entities in this model. Required to call prior to using the model.
     * @return boolean, returns false if any exceptions are encountered
     */
    public boolean loadEntities() {
        int failedToParse = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(super.CSV_FILE))) {
            String line = reader.readLine();

            while (line != null) {
                if (super.lineIsValid(line) && parseEntity(line)) {
                } else {
                    super.addLogMessage(LogLevel.WARNING, "Could not parse line \n'" + line + "'' in location model; line was invalid.");
                    failedToParse++;
                }

                line = reader.readLine();
            }

            return true;
        } catch (IOException ex) {
            super.addLogMessage(LogLevel.FATAL, "Could not locate file in model.");
            return false;
        } finally {
            super.addLogMessage(LogLevel.VERBOSE,
                        "Model has finished loading entities."
                        + "\n\t" + this.entities.size() + " successfully entities parsed."
                        + "\n\t" + failedToParse + " entities failed to parsed.", true);
        }
    }

    private boolean parseEntity(final String line) {
        int i = 0;

        try {
            String[] fields = line.split(",");
            Restaurant location = new Restaurant(fields[0]);
            location.setOriginalData(line);

//            i = 0;
//            for (Consumer<Object> consumer : location.getLoadOrder()) {
//                consumer.accept(fields[i++]);
//            }

            this.entities.put(location.getID(), location);
        } catch (NumberFormatException ex) {
            super.addLogMessage(LogLevel.FATAL, "Could not parse entities in model.", true);
            return false;
        }

        return true;
    }

    /**
     * Retrieves ALL entities from this model.
     * @return Entity
     */
    public Collection<Restaurant> getEntities() {
        return this.entities.values();
    }

    /**
     * Checks to see if the given UID for an entity in this model exists. Returns true if found.
     * @param UID unique ID of the entity
     * @return boolean, true if found
     */
    public boolean entityExists(final String UID) {
        return (this.entities.containsKey(UID));
    }
}