package de.flower.common.util;

import com.google.common.base.Predicate;

import java.text.MessageFormat;

/**
 * @author flowerrrr
 */
public class NameFinder {

    public static String generate(String base, String format, Predicate<String> predicate) {
        int i = 1;
        while (true) {
            String candidate = MessageFormat.format(format + "{1}", base, (i == 1) ? "" : "-" + i);
            if (predicate.apply(candidate)) {
                return candidate;
            }
            i++;
        }
    }

    public static String delete(String base, Predicate<String> predicate) {
        return generate(base, "DELETED-{0}", predicate);
    }
}
