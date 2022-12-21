package Utilities;

import java.text.DecimalFormat;

public class FormatText {
    /**
     * Returns the given decimal number in the standard accounting format.
     * @param number {@code double}
     * @param decimalPlace, how many numbers to the right of the decimal to show
     * @param includeDollarSign will output with the dollar sign ($) before the numbers
     * @return String representation of number
     */
    public static String format(final double number, int decimalPlace, boolean includeDollarSign) {
        String decimalString = "0".repeat(decimalPlace);
        return (
            (includeDollarSign ? "$" : "") // Show dollar sign option
            + new DecimalFormat("#,###" + (decimalPlace > 0 ? "." : "") + decimalString)
            .format(number) // Final call to format this number
        );
    }
}
