package de.flower.rmt.util;

import de.flower.rmt.util.prettytime.PrettyTime;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Central class everybody has to use when formatting dates.
 *
 * @author flowerrrr
 */
public class Dates {

    public static final String DATE_MEDIUM = "dd.MM.yyyy";

    public static final String DATE_SHORT = "dd.MM.";

    public static final String DATE_SHORT_WITH_WEEKDAY = "EE. dd.MM.";

    public static final String DATE_MEDIUM_WITH_WEEKDAY = "EE. dd.MM.yy";

    public static final String TIME_SHORT = "hh:mm";

    public static final String DATE_TIME_SHORT = DATE_SHORT + " " + TIME_SHORT;

    public static String formatDateTimeMedium(final Date date) {
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(date);
    }

    public static String formatDateMediumWithWeekday(final Date date) {
        return new SimpleDateFormat(DATE_MEDIUM_WITH_WEEKDAY).format(date);
    }

    public static String formatDateTimeShort(final Date date) {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
    }

    public static String formatDateTimeShort(final DateTime dateTime) {
        return formatDateTimeShort(dateTime.toDate());
    }

    public static String formatDateTimeShortWithWeekday(final Date date) {
        return formatDateShortWithWeekday(date) + " " + formatTimeShort(date);
    }

    public static String formatDateLong(final Date date) {
        return DateFormat.getDateInstance(DateFormat.LONG).format(date);
    }

    public static String formatDateShortWithWeekday(final Date date) {
        return new SimpleDateFormat(DATE_SHORT_WITH_WEEKDAY).format(date);
    }

    public static String formatDateShort(final Date date) {
        return new SimpleDateFormat(DATE_SHORT).format(date);
    }

    public static String formatDateMedium(final Date date) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
    }

    public static String formatTimeMedium(final Date date) {
        return DateFormat.getTimeInstance(DateFormat.MEDIUM).format(date);
    }

    public static String formatTimeShort(final LocalTime time) {
        return formatTimeShort(time.toDateTimeToday().toDate());
    }

    public static String formatTimeShort(final Date date) {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
    }

    /**
     * Converts a date into something like
     * '14 min ago' (< 12h),
     * 'Today at 6:30 (> 12 h),
     * 'Yesterday at 12:30' (>12h <day before yesterday),
     * 'Friday at 2:43' (> day before yesterday) or
     * 'April 10 at 6:54' (> one week).
     *
     * @param date
     * @return
     */
    public static String formatFacebook(final Date date) {
        PrettyTime pt = new PrettyTime();
        return pt.format(date);
    }
}
