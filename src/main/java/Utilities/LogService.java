package Utilities;

import Constants.LogLevel;
import Models.LogMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple logging service to handle persisting of data throughout the program. Can be exported and viewed
 * in the application.
 *
 * Offers the ability to filter log messages by common filters, such as by warning level.
 *
 * @author Andrew Michael
 */
public final class LogService {
    private final String fileName;
    private boolean verboseLogging = false;
    private final List<LogMessage> logs = new ArrayList<>();

    /**
     * Instantiates a new log service.
     * @param fileName {@code String} name of the file being logged.
     */
    public LogService(final String fileName) {
        this.fileName = fileName;
    }


    /**
     * Toggles the verbose logging to enable. When enabled, verbose-level messages will be outputted when
     * they are created. By default, these messages will only be logged and not outputted.
     */
    public void setVerbose() { this.verboseLogging = true; }

    /**
     * Logs a successful message to the list. Common examples include: entity was persisted without errors,
     * statistics were generated successfully.
     * @param msg {@code String} message to be logged.
     */
    public void logSuccess(final String msg) {
        logs.add(newLogMessage(LogLevel.SUCCESS, fileName, msg));
    }

    /**
     * Logs a warning message to the list. Common examples include: entity was not persisted, model could not
     * return an entity, or another internal exception (that did not crash the app) was thrown. Use WARNING when
     * the application did not crash.
     * @param msg {@code String} message to be logged.
     */
    public void logWarning(final String msg) {
        logs.add(newLogMessage(LogLevel.WARNING, fileName, msg));
        System.out.println(LogLevel.WARNING + fileName + ": " + msg);
    }

    /**
     * Logs a fatal error message to the list. If the app is going to crash or user experience is going to suffer
     * significantly, then use this log level.
     * @param msg {@code String} message to be logged.
     */
    public void logFatal(final String msg, final Exception ex) {
        logs.add(newLogMessage(LogLevel.FATAL, fileName, msg));
        System.out.println(LogLevel.FATAL + fileName + ": " + msg + "\n" + ex + "\n\n");
        Debugger.output(ex, this.fileName);
    }

    /**
     * Logs a verbose message to the list. For logs that are meant to inform development or users of non-impactful
     * issues, this log level is perfect. By default, verbose logging is disabled.
     * @param msg {@code String} message to be logged.
     */
    public void logVerbose(final String msg) {
        logs.add(newLogMessage(LogLevel.VERBOSE, fileName, msg));
        if (this.verboseLogging)
            System.out.println(LogLevel.VERBOSE + fileName + ": " + msg);
    }

    /**
     * Returns all the logs from this service.
     * @return list
     */
    public List<LogMessage> getLogs() {
        return this.logs;
    }

    /**
     * Adds an existing log to the logger's logs.
     * @param log {@code LogMessage} log.
     */
    public void addLog(LogMessage log) { this.logs.add(log); }

    /**
     * Adds a group of existing logs to the logger's logs.
     * @param logs {@code Array of LogMessage} logs.
     */
    public void addManyLogs(LogMessage[] logs) { this.logs.addAll(Arrays.stream(logs).toList()); }

    /**
     * Filter logs by warning list.
     *
     * @return the list
     */
    public List<LogMessage> filterLogsByWarning() {
        return this.logs.stream().filter(log -> log.getLevel().equals(LogLevel.WARNING)).toList();
    }

    /**
     * Filter logs by warning list.
     *
     * @param query {@code String} to be searched
     * @return the list
     */
    public List<LogMessage> filterLogsByWarning(final String query) {
        return this.logs.stream()
                .filter(log -> log.getLevel().equals(LogLevel.WARNING))
                .filter(v -> v.getMessage().contains(query))
                .toList();
    }

    /**
     * Filter logs by fatal list.
     *
     * @return the list
     */
    public List<LogMessage> filterLogsByFatal() {
        return this.logs.stream().filter(log -> log.getLevel().equals(LogLevel.FATAL)).toList();
    }

    /**
     * Filter logs by verbose list.
     *
     * @param query {@code String} to be searched
     * @return the list
     */
    public List<LogMessage> filterLogsByVerbose(final String query) {
        return this.logs.stream()
                .filter(log -> log.getLevel().equals(LogLevel.VERBOSE))
                .filter(v -> v.getMessage().contains(query))
                .toList();
    }

    /**
     * Adds a new {@code LogMessage} to the list of log messages for this model.
     * TODO revisit this class
     * @param level {@code LogLevel} prefix and category of the message.
     * @param title {@code String} header of the message.
     * @param message {@code LogMessage} to log.
     */
    private LogMessage newLogMessage(final LogLevel level, final String title, final String message) {
        return new LogMessage(level, title, message);
    }
}
