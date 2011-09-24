package de.flower.common.util;

import org.apache.commons.lang3.Validate;

/**
 * @author flowerrrr
 */
public class Check extends Validate {

    public static void isEqual(Object actual, Object expected) {
        notNull(expected);
        notNull(actual);
        if (!expected.equals(actual)) {
            throw new IllegalArgumentException(format(actual, expected, null));
        }
    }

    static String format(Object actual, Object expected, String message) {
        String formatted = "";
        if (null != message) {
            formatted = message + " ";
        }

        return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
    }


}
