package de.flower.common.test;

import java.util.List;

/**
 * If anybody knows a lib implementing any of these functions please
 * feel free to replace or remove the code here.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * Checks if a substring is contained in any of the given strings.
     *
     * @param substring the substring
     * @param strings the strings
     * @return true, if successful
     */
    public static boolean containedInAny(final String substring, final List<String> strings) {
        for (final String string : strings) {
            if (string.contains(substring)) {
                return true;
            }
        }
        return false;
    }

}
