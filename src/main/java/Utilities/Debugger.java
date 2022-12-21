package Utilities;

import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Custom-built solution for outputting exception messages and their root causes. Attempts to find the
 * class that actually threw a given exception.
 *
 * <p>
 * Meant to be more of an active development use and not for production.
 */
public class Debugger {
    public static void output(Exception ex, String filename) {
        System.out.println("------------------------------------");
        Throwable rootCause = getRootException(ex);
        String fileName = (filename.contains(".java") ? "" : filename + ".java"); // Add .java if necessary

        System.out.println(
            "\t   - E R R O R -" +
            "\nOrigins: '" + fileName + "'." +
            "\n\nCause: '" + rootCause + "'." +
            "\n\nStack Trace:"
        );

        StackTraceElement[] stackTrace = ex.getStackTrace();
        OptionalInt indexOpt = IntStream.range(0, stackTrace.length)
                .filter(i -> fileName.toLowerCase().equals(
                    ex.getStackTrace()[i].getFileName().toLowerCase())
                ).findFirst();

        try {
            for (int i = (indexOpt.getAsInt() - 1); i < (indexOpt.getAsInt() + 2); i++) {
                if (stackTrace[i] != null) {
                    StackTraceElement trace = stackTrace[i];

                    System.out.println("\n #" + trace.getLineNumber() + ": " + trace.getFileName() + "\nMethod: "
                            + trace.getMethodName() + "\nClass:  " + trace.getClassName());
                }
            }
        } catch (NoSuchElementException internalException) {
            System.out.println("\t! Internal Exception !\nFilename was not found in stack.\n");
            StackTraceElement trace = ex.getStackTrace()[0];

            System.out.println("\n #" + trace.getLineNumber() + ": " + trace.getFileName() + "\nMethod: "
                + trace.getMethodName() + "\nClass:  " + trace.getClassName());
        } catch (Exception internalException) {
            StackTraceElement trace = stackTrace[indexOpt.getAsInt()];

            System.out.println("\n #" + trace.getLineNumber() + ": " + trace.getFileName() + "\nMethod: "
                + trace.getMethodName() + "\nClass:  " + trace.getClassName());
        }

        System.out.println("------------------------------------");
    }

    private static Throwable getRootException(Throwable exception) {
        Throwable rootException = exception;
        while (rootException.getCause() != null) {
            rootException = rootException.getCause();
        }
        return rootException;
    }
}
