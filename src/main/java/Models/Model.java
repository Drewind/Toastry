package Models;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import Constants.LogLevel;
import Entities.Entity;
import Interfaces.ViewActionObserver;


/**
 * Handles interactions with the database.
 * In order to properly save and load data, we will need the following parameters pass
 * to and saved on this model.
 *
 * <p>
 * <b>csvFilePath</b> a {@code String} to the file path as we will create the path/file here.
 * <b>expectedFields</b> {@code int} is the exact number of fields in a line we expect when validating a line.
 * </p>
 */
public class Model {
    protected final File CSV_FILE;
    protected final Path CSV_PATH;
    private final int EXPECTED_FIELDS;
    private boolean modelLocked;
    protected ArrayList<ViewActionObserver> observers = new ArrayList<>();
    private final List<LogMessage> logMessages = new ArrayList<>();


    /**
     * Instantiates a new model object. Accepts two arguments, a file object and
     * @param csvFilePath {@code String} a string path pointing to the CSV file.
     * @param expectedFields {@code int} how many fields we are expecting when validating a line.
     */
    public Model(final String csvFilePath, final int expectedFields) {
        this.CSV_FILE = new File(csvFilePath);
        this.CSV_PATH = Paths.get(csvFilePath);
        this.EXPECTED_FIELDS = expectedFields;
    }


    /**
     * Edits an existing entity in this model. Requires to be overridden by its children.
     */
    public void editEntity(Entity entity) throws Exception {
        if (modelLocked) { throw new Exception(LogLevel.WARNING + "Model is locked."); }
        modelLocked = true;

        // Confirms the line exists in the CSV file.
        if (getIndexFromFile(entity.getID()).isPresent()) {
            List<String> lines = Files.readAllLines(CSV_PATH);

            // Create a new line based on the entity's ordered save list. Joins the results with a comma.
            String newLine = entity.getSerializationChain().stream().map(fun -> fun.get().toString()).collect(Collectors.joining(","));

            // Write to the file. Grabs the index in the file via getIndexFromFile.
            lines.set(getIndexFromFile(entity.getID()).get(), newLine);
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            entity.resetChangedState();
            for (ViewActionObserver observer : this.observers) { observer.notifyModifiedEntity(entity); }
            addLogMessage(LogLevel.SUCCESS, "Persisted changes to UID '" + entity.getID() + "'.");
        } else {
            addLogMessage(LogLevel.WARNING, "Could not persist changes to entity " + entity.getID() + " because no such entity was found in CSV. "
                        + "Double-check the CSV file for the entity.");
            throw new Exception(LogLevel.WARNING + "Could not find entity (" + entity.getID() + ") in CSV.");
        }
        modelLocked = false;
    }


    /**
     * Removes an entity from the database.
     * @param entity Entity
     */
    public void deleteEntity(Entity entity) throws Exception {
        if (modelLocked) { throw new Exception(LogLevel.WARNING + "Model is locked."); }
        modelLocked = true;
        
        // Removes the requested entity from the database using a stream and filter.
        // Acts like a while loop, building up the list that we'll need to replace the file anyway.
        List<String> lines = Files.readAllLines(CSV_PATH).stream()
            .filter(line -> !line.contains(entity.getID()))
            .toList();

        Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
        modelLocked = false;
    }


    /**
     * Adds an entity to the database.
     */
    public void addEntity(Entity entity) throws Exception {
        if (modelLocked) { throw new Exception(LogLevel.WARNING + "Model is locked."); }
        modelLocked = true;

        List<String> lines = Files.readAllLines(CSV_PATH);

        // Create a new line based on the entity's ordered save list. Joins the results with a comma.
        String newLine = entity.getSerializationChain()
                .stream().map(fun -> fun.get().toString())
                .collect(Collectors.joining(","));
        System.out.println(" >> NEWLINE: '" + newLine + "'.");

        // Verify the line isn't null.
        if (newLine.equals("")) {
            addLogMessage(LogLevel.WARNING, "Could not persist entity " + entity.getID() + " because the payload was null. "
                        + "Did you create a valid subtype with a defined save order?");
            throw new Exception(LogLevel.WARNING + "Line is invalid.");
        }

        // Persist the entity.
        lines.add(newLine);

        Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
        modelLocked = false;
    }


    /**
     * Retrieves an index from the file based on the UID passed to it.
     * @param UID unique identifier
     * @return int
     */
    public Optional<Integer> getIndexFromFile(String UID) {
        Optional<Integer> index;
        
        try {
            List<String> lines = Files.readAllLines(CSV_PATH);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(UID)) {
                    index = Optional.of(i);
                    System.out.println(" >> getIndexFromFile found index #" + index + ".\n\t" + i); // todo remove this line
                    return index;
                }
            }
        } catch (IOException ex) {
            addLogMessage(LogLevel.WARNING, "Corrupt data in model. Could not find index in CSV file for " + UID);
        }
        return Optional.empty();
    }


    /**
     * lineIsValid
     * Checks whether the index range exists in a CSV row.
     * @param data String[]
     * @return bool
     */
    protected boolean lineIsValid(String data) {
        if (data == null || data.equals("") || data.split(",").length != this.EXPECTED_FIELDS) return false;

        String[] fields = data.split(",");
        for (String field : fields) {
            if (field == null) return false;
        }

        return true;
    }


    /**
     * Converts a string representing a map to an actual map.
     * @param str String
     * @return {@code Map<String, String>}
     * @see <a href="https://stackoverflow.com/questions/26485964/how-to-convert-string-into-hashmap-in-java">Stackoverflow</a> for original implementation.
     */
    public static Map<String, String> convert(String str) {
        Map<String, String> map = new HashMap<>();
        String[] tokens = str
                .substring(1, str.length() - 1) // Trim input of brackets
                .split("\\|");            // Split on

        for (String pair : tokens) {
            String[] entry = pair.split("=");
            map.put(entry[0].trim(), entry[1].trim());
        }

//        for (int i = 0; i < tokens.length-1; )
//            map.put(tokens[i++], tokens[i++]);
        return map;
    }


    /**
     * Registers a new observer for this model.
     * @param observer {@code observer} a entity that implements ViewActionObserver interface.
     */
    public void registerObserver(ViewActionObserver observer) {
        this.observers.add(observer);
    }


    /**
     * Removes an observer from the model.
     * @param observer {@code observer} a entity that implements ViewActionObserver interface.
     */
    public void removeObserver(ViewActionObserver observer) {
        this.observers.remove(observer);
    }


    /**
     * Adds a new {@code LogMessage} to the list of log messages for this model.
     * @param msg {@code LogMessage} to log.
     */
    protected void addLogMessage(LogLevel level, String msg) {
        this.logMessages.add(new LogMessage(level, "Model", msg));
    }

    /**
     * Adds a new {@code LogMessage} to the list of log messages for this model.
     * Will also print to console.
     * @param msg {@code LogMessage} to log.
     */
    protected void addLogMessage(LogLevel level, String msg, boolean outputToConsole) {
        LogMessage log = new LogMessage(level, "Model", msg);
        this.logMessages.add(log);
        System.out.println(log);
    }


    /**
     * Saves a list of warnings and errors collected during the execution of the model to a file.
     * todo move this to a singleton
     */
    public void saveReport(final String fileName) {
        final Path PATH = Paths.get(fileName);
        List<String> fileContent = new ArrayList<>(this.logMessages.size());

        for (LogMessage log : this.logMessages) {
            fileContent.add(log.toString());
        }

        try {
            Files.write(PATH, fileContent, StandardCharsets.UTF_8);
            System.out.println(LogLevel.VERBOSE + "Wrote log file to path \n" + fileName);
        } catch(IOException ex) {
            addLogMessage(LogLevel.WARNING, "Could not save log messages to file.\n" + ex, true);
        }
    }
}
