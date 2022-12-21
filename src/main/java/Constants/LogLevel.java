package Constants;

/**
 * Unified logging level messages. Defines the standard prefix for each log level that is appended prior to the actual
 * message.
 */
public enum LogLevel {
    SUCCESS ("[SUCCESS]: "),
    WARNING ("[WARNING]: "),
    FATAL ("<[ FATAL ]> "),
    DEBUG ("[ DEBUG ]  "),
    VERBOSE("VERBOSE: ");

    private final String name;       

    LogLevel(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}