package de.flower.rmt.util.prettytime.i18n;

import java.util.ListResourceBundle;

public class Resources extends ListResourceBundle {

    private static final Object[][] OBJECTS = new Object[][]{
            {"JustNowPastPattern", "Soeben"},
            {"MinutePastPattern", "Vor {0} Minute"},
            {"MinutePastPatternPlural", "Vor {0} Minuten"},
            {"HourPastPattern", "Vor {0} Stunde"},
            {"HourPastPatternPlural", "Vor {0} Stunden"},
            {"TodayPattern", "Heute um {0}"},
            {"YesterdayPattern", "Gestern um {0}"},
            {"WeekPattern", "{0} um {1}"},
            {"DefaultPattern", "{0} um {1}"}
    };

    @Override
    protected Object[][] getContents() {
        return OBJECTS;
    }
}