package de.flower.common.util;

import org.apache.commons.lang3.Validate;

/**
 * @author flowerrrr
 */
public class Check extends Validate {

    public static void isEqual(Object actual, Object expected, String message) {
        notNull(expected);
        notNull(actual);
        if (!expected.equals(actual)) {
            throw new IllegalArgumentException(format(actual, expected, message));
        }
    }

    private static String format(Object actual, Object expected, String message) {
        String formatted = "";
        if (null != message) {
            formatted = message + " ";
        }

        return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
    }

}
