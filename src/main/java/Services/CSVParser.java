package Services;

import Utilities.LogService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Service to be injected into classes inheriting from {@code DeserializeEntityInterface}.
 * These deserialization classes use this parser class to safely convert CSV database content
 * into their respective Java types. All calls to this class will always return a non-null value; making
 * it safe to call even with potential null values. If an exception occurs, the method will simply return
 * a default value. Some methods offer a OrDefault method which allows the user to specify a
 * default value to be returned should an exception occur.
 *
 * TODO: consider how to implement failedToParse into this
 */
public class CSVParser {
    private final List<String> fields;
    private final LogService logger;
    private String entityID;

    public CSVParser(final LogService logger, final String str) {
        this.fields = Arrays.stream(str.split(",")).toList();
        this.logger = logger;
        this.entityID = parseGUID(0);
    }

    /**
     * Converts field with {@code index} from the stored fields into a {@code String}.
     * @param index the index
     * @return the string
     */
    public String parseString(final int index) {
        try {
            return this.fields.get(index);
        } catch (Exception ex) {
            parsingException(ex, index);
            return "";
        }
    }

    /**
     * Converts field with {@code index} from the stored fields into a {@code String} or will return {@code defaultStr}.
     * @param index      the index
     * @param defaultStr the default str
     * @return the string
     */
    public String parseStringOrDefault(final int index, final String defaultStr) {
        try {
            return this.fields.get(index);
        } catch (Exception ex) {
            this.logger.logVerbose("Failed to parse index " + index + " in record; defaulting to "
                    + defaultStr + ".");
            return defaultStr;
        }
    }

    /**
     * Converts field with {@code index} from the stored fields into a {@code boolean}.
     * @param index the index
     * @return the boolean
     */
    public boolean parseBoolean(final int index) {
        try {
            return (this.fields.get(index).equals("true"));
        } catch (Exception ex) {
            parsingException(ex, index);
            return false;
        }
    }

    /**
     * Converts field with {@code index} from the stored fields into a {@code double}.
     * @param index the index
     * @return the double
     */
    public Double parseDouble(final int index) {
        try {
            return Double.parseDouble(this.fields.get(index));
        } catch(Exception ex) {
            parsingException(ex, index);
            return 0.00;
        }
    }

    /**
     * Converts field with {@code index} from the stored fields into a {@code double} formatted with two decimal places.
     * @param index the index
     * @return the double
     */
    public Double parseCurrency(final int index) {
        try {
            return round(Double.parseDouble(this.fields.get(index)), 2);
        } catch(Exception ex) {
            parsingException(ex, index);
            return 0.00;
        }
    }

    /**
     * Converts field with {@code index} from the stored fields into a {@code integer}.
     * @param index the index
     * @return the integer
     */
    public Integer parseInteger(final int index) {
        try {
            return Integer.parseInt(this.fields.get(index));
        } catch(Exception ex) {
            parsingException(ex, index);
            return 0;
        }
    }

    public List<String> parseListString(final int index) {
        try {
            String dbLine = this.fields.get(index).substring(1, this.fields.get(index).length() - 1);
            return Arrays.stream(dbLine.split("\\|")).filter(Predicate.not(String::isBlank)).toList();
        } catch (Exception ex) { parsingException(ex, index); }
        return List.of();
    }

    private void parsingException(final Exception ex, final int index) {
        this.logger.logWarning("Failed to parse #" + this.entityID + " field with index " + index
                + "; exception '" + ex.getMessage() + "' was thrown.");
    }

    public String parseGUID(final int index) {
        try {
            String idField = this.fields.get(index);
            long dashCount = idField.chars().filter(c -> c == '-').count();
            return (idField.length() == 36 && dashCount == 4 ? idField : "");
        } catch (Exception ex) {
            parsingException(ex, index);
            return "";
        }
    }

    /**
     * A simple rounding algorithm.
     * @param value
     * @param places
     * @author <a href="https://stackoverflow.com/a/2808648/6598810">Jonik's SO answer</a>
     * @return
     */
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
