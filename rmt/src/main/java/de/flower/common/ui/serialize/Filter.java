package de.flower.common.ui.serialize;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author flowerrrr
 */
public class Filter {

    private List<String> inclusionPatterns = new ArrayList<String>();

    private List<String> exclusionPatterns = new ArrayList<String>();

    public Filter(final String ... inclusionPatterns) {
        for (String inclusionPattern : inclusionPatterns) {
            this.inclusionPatterns.add(inclusionPattern);
        }
    }

    public void addInclusion(final String pattern) {
        this.inclusionPatterns.add(pattern);
    }

    public void addExclusion(final String pattern) {
        this.exclusionPatterns.add(pattern);
    }

    public List<String> matches(final String string) {
        final List<String> matches = matches(inclusionPatterns, string);
        for (String match : new ArrayList<String>(matches)) {
            if (!matches(exclusionPatterns, match).isEmpty()) {
                matches.remove(match);
            }
        }
        return matches;
    }

    public static List<String> matches(List<String> patterns, String string) {
        List<String> matches = new ArrayList<String>();
        for (String pattern : patterns) {
            Matcher m = Pattern.compile(pattern).matcher(string);
            while (m.find()) {
                final String match = m.group();
                matches.add(match);
            }
        }
        return matches;
    }
}
