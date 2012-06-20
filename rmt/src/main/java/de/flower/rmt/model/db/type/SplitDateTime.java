package de.flower.rmt.model.db.type;

import de.flower.common.util.Check;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.Date;

/**
 * Extended datetime with separate getter/setter for date and time part to be used with property models
 * in wicket forms that use two components to edit the dateTime (a datepicker and a time-dropdown).
 *
 * @author flowerrrr
 */
@Deprecated // not used
public class SplitDateTime {

    private DateTime dateTime;

    public SplitDateTime() {

    }

    public SplitDateTime(final Object timestamp) {
        this.dateTime = new DateTime(timestamp);
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(final DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Date toDate() {
        return dateTime.toDate();
    }

    public Date getDate() {
        return (dateTime == null) ? null : dateTime.toDate();
    }

    public void setDate(Date date) {
        Check.notNull(date);
        // normalize to midnight
        updateDateTime(new LocalDate(date).toDate());
    }

    public LocalTime getTime() {
        return (dateTime == null) ? null : dateTime.toLocalTime();
    }

    public void setTime(LocalTime time) {
        Check.notNull(time);
        updateDateTime(time);
    }

    private void updateDateTime(Date date) {
        LocalTime time = getTime();
        if (time == null) {
            dateTime = new DateTime(date);
        } else {
            dateTime = new DateTime(date).withFields(time);
        }
    }

    private void updateDateTime(LocalTime time) {
        if (dateTime == null) {
            dateTime = new DateTime(0).withFields(time);
        } else {
            dateTime = dateTime.withFields(time);
        }
    }

    @Override
    public String toString() {
        return "SplitDateTime{" +
                "dateTime=" + dateTime +
                ",date=" + getDate() +
                ",time=" + getTime() +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SplitDateTime that = (SplitDateTime) o;

        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return dateTime != null ? dateTime.hashCode() : 0;
    }
}

