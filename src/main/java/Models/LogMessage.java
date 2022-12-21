package Models;

import java.sql.Timestamp;

import Constants.LogLevel;

/**
 * A model to keep track of warnings and errors during execution on models.
 */
public class LogMessage {
    private final String TITLE;
    private final String MESSAGE;
    private final LogLevel LOG_LEVEL;
    private final Timestamp TIMESTAMP = new Timestamp(System.currentTimeMillis());
    
    public LogMessage(final LogLevel LEVEL, final String TITLE, final String MESSAGE) {
        this.TITLE = TITLE;
        this.MESSAGE = MESSAGE;
        this.LOG_LEVEL = LEVEL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getMessage() {
        return MESSAGE;
    }

    public LogLevel getLevel() { return LOG_LEVEL; }

    /**
     * Returns a formatted string to be printed in a file or console for debugging.
     */
    @Override
    public String toString() {
        return String.format("%26s %10s %s: %s",
            TIMESTAMP,
            LOG_LEVEL.toString(),
            TITLE,
            MESSAGE
        );
    }
}
