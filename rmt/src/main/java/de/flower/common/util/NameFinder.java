package de.flower.common.util;

import com.google.common.base.Predicate;

import java.text.MessageFormat;


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

    /**
     * Generate new name. Format: "<base> x", x=1,2,...
     *
     * @param base      the base for the new name
     * @param suffixes  array of suffixes, e.g. [1,2,3,4] or ["A", "B", "C"]
     * @param predicate the predicate to test if new name is ok.
     * @return the new name.
     */
    public static String newName(final String base, final Object[] suffixes, final Predicate<String> predicate) {
        return generate(base, predicate, 1, suffixes);
    }

    /**
     * Generate new name. Format: "<old> x", x=2,3,...
     *
     * @param old       the old name
     * @param suffixes  array of suffixes, e.g. [1,2,3,4] or ["A", "B", "C"]
     * @param predicate the predicate to test if new name is ok.
     * @return the new name.
     */
    public static String copy(final String old, final Object[] suffixes, final Predicate<String> predicate) {
        return generate(old, predicate, 2, suffixes);
    }

    private static String generate(final String base, final Predicate<String> predicate, final int startIndex, Object[] suffixes) {

        int i = startIndex;
        while (true) {
            final String name = base + " " + suffixes[i - 1];
            if (predicate.apply(name)) {
                return name;
            }
            i++;
        }
    }
}
