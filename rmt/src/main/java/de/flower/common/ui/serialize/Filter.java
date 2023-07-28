package de.flower.common.ui.serialize;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Filter {

    public enum MatchType {
        WHITELIST,
        BLACKLIST,
        NOMATCH;
    }

    private List<String> whiteList = new ArrayList<String>();

    private List<String> blackList = new ArrayList<String>();

    public Filter() {
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(final List<String> whiteList) {
        this.whiteList = whiteList;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public void setBlackList(final List<String> blackList) {
        this.blackList = blackList;
    }

    public MatchType matches(final String string) {
        if (!matches(blackList, string).isEmpty()) {
            return MatchType.BLACKLIST;
        } else if (!matches(whiteList, string).isEmpty()) {
            return MatchType.WHITELIST;
        } else {
            return MatchType.NOMATCH;
        }
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
