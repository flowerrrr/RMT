package de.flower.rmt.util.prettytime;

import de.flower.rmt.util.Dates;
import de.flower.rmt.util.prettytime.i18n.Resources;
import org.joda.time.DateTime;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public abstract class AbstractDateHandler implements IDateHandler {

    private ResourceBundle bundle;

    protected Locale locale;

    public AbstractDateHandler(Locale locale) {
        // Resource bundles need to be in the given package, names start with
        // 'Resources', e.g. 'Resources_de.java'
        this.locale = locale;
        this.bundle = ResourceBundle.getBundle(Resources.class.getName(), locale);
    }

    protected final String format(String resourceKey, Object... args) {
        String pattern = bundle.getString(resourceKey);
        return MessageFormat.format(pattern, args);
    }

    protected final String getPlural(long value) {
        return (value > 1) ? "Plural" : "";
    }

    public static boolean isSameDay(final DateTime d1, final DateTime d2) {
        return (d1.dayOfYear().equals(d2.dayOfYear()) && d1.year().equals(d2.year()));
    }

    public static class JustNow extends AbstractDateHandler {

        public JustNow(Locale locale) {
            super(locale);
        }

        @Override
        public boolean canHandle(final Date date, final long delta) {
            long deltaMinutes = TimeUnit.MILLISECONDS.toMinutes(delta);
            return (deltaMinutes < 1) ? true : false;
        }

        @Override
        public String format(final Date date, final long delta) {
            return format("JustNowPastPattern");
        }
    }

    public static class Minute extends AbstractDateHandler {

        public Minute(Locale locale) {
            super(locale);
        }

        @Override
        public boolean canHandle(final Date date, final long delta) {
            long deltaMinutes = TimeUnit.MILLISECONDS.toMinutes(delta);
            return (deltaMinutes < 60) ? true : false;
        }

        @Override
        public String format(final Date date, final long delta) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(delta);
            return format("MinutePastPattern" + getPlural(minutes), minutes);
        }
    }

    public static class Hour extends AbstractDateHandler {

        public Hour(Locale locale) {
            super(locale);
        }

        @Override
        public boolean canHandle(final Date date, final long delta) {
            long deltaHours = TimeUnit.MILLISECONDS.toHours(delta);
            return (deltaHours <= 12) ? true : false;
        }

        @Override
        public String format(final Date date, final long delta) {
            long hours = TimeUnit.MILLISECONDS.toHours(delta);
            return format("HourPastPattern" + getPlural(hours), hours);
        }
    }

    public static class Today extends AbstractDateHandler {

        public Today(Locale locale) {
            super(locale);
        }

        @Override
        public boolean canHandle(final Date date, final long delta) {
            DateTime referenceDate = new DateTime(date).plus(delta);
            DateTime jDate = new DateTime(date);
            return isSameDay(referenceDate, jDate);
        }

        @Override
        public String format(final Date date, final long delta) {
            return format("TodayPattern", Dates.formatTimeShort(date));
        }
    }

    public static class Yesterday extends AbstractDateHandler {

        public Yesterday(Locale locale) {
            super(locale);
        }

        @Override
        public boolean canHandle(final Date date, final long delta) {
            DateTime referenceDate = new DateTime(date).plus(delta);
            DateTime yesterDay = referenceDate.minusDays(1);
            DateTime jDate = new DateTime(date);
            return (isSameDay(yesterDay, jDate));
        }

        @Override
        public String format(final Date date, final long delta) {
            return format("YesterdayPattern", Dates.formatTimeShort(date));
        }
    }

    public static class Week extends AbstractDateHandler {

        public Week(Locale locale) {
            super(locale);
        }

        @Override
        public boolean canHandle(final Date date, final long delta) {
            long deltaDays = TimeUnit.MILLISECONDS.toDays(delta);
            return (deltaDays <= 6) ? true : false;
        }

        @Override
        public String format(final Date date, final long delta) {
            String sDay = new SimpleDateFormat("EEEE", locale).format(date);
            return format("WeekPattern", sDay, Dates.formatTimeShort(date));
        }
    }

    public static class Default extends AbstractDateHandler {

        public Default(Locale locale) {
            super(locale);
        }

        @Override
        public boolean canHandle(final Date date, final long delta) {
            return true;
        }

        @Override
        public String format(final Date date, final long delta) {
            return format("DefaultPattern", Dates.formatDateLong(date), Dates.formatTimeShort(date));
        }
    }
}
