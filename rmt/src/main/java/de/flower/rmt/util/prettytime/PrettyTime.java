package de.flower.rmt.util.prettytime;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Pretty formatting of dates. Inspired by com.ocpsoft.pretty.time.PrettyTime.
 * Adds support for Today and Yesterday.
 */
public class PrettyTime {

    // use same locale lookup as in DateFormat.xxxx
    private Locale locale = Locale.getDefault(Locale.Category.FORMAT);

    private List<IDateHandler> handlers = new ArrayList<>();

    private Date reference = new Date();

    public PrettyTime() {
        initHandlers();
    }

    public PrettyTime(final Date reference) {
        this();
        this.reference = reference;
    }

    private void initHandlers() {
        handlers.add(new AbstractDateHandler.JustNow(locale));
        handlers.add(new AbstractDateHandler.Minute(locale));
        handlers.add(new AbstractDateHandler.Hour(locale));
        handlers.add(new AbstractDateHandler.Today(locale));
        handlers.add(new AbstractDateHandler.Yesterday(locale));
        handlers.add(new AbstractDateHandler.Week(locale));
    }

    public String format(final Date date) {
        IDateHandler dateHandler = getDateHandler(date);
        return dateHandler.format(date, getDelta(date));
    }

    private IDateHandler getDateHandler(final Date date, final long delta) {
        for (IDateHandler handler : handlers) {
            if (handler.canHandle(date, delta)) {
                return handler;
            }
        }
        return new AbstractDateHandler.Default(locale);
    }

    @VisibleForTesting
    protected IDateHandler getDateHandler(final Date date) {
        return getDateHandler(date, getDelta(date));
    }

    private long getDelta(Date date) {
        return reference.getTime() - date.getTime();
    }
}
