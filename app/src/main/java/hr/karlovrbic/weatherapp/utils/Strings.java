package hr.karlovrbic.weatherapp.utils;

/**
 * {@code Strings} is a utility class that offers static methods that do some tasks with {@linkplain String strings}.
 *
 * @author Karlo Vrbic
 * @version 1.0
 */
public class Strings {

    private Strings() {
    }

    public static String requireNonNullAndNonEmpty(String string, String message) {
        return requireNonNullAndNonEmpty(string, message, message);
    }

    public static String requireNonNullAndNonEmpty(String string, String nullMessage, String emptyMessage) {
        if (string == null) {
            throw new NullPointerException(nullMessage);
        }
        String output = string.trim();
        if (output.isEmpty()) {
            throw new IllegalArgumentException(emptyMessage);
        }
        return output;
    }
}
