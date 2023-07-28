package de.flower.rmt.ui.markup.html.calendar;

import java.util.Date;

 
public class CalEvent {

    public String id;

    public Long entityId;

    /**
     * Entity class.
     */
    public String clazzName;

    public String title;

    public Date start;

    public Date end;

    public boolean allDay;

    public String url;

    /**
     * Css class.
     */
    public String className;

    public boolean isNew() {
        return entityId == null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CalEvent calEvent = (CalEvent) o;

        if (id != null ? !id.equals(calEvent.id) : calEvent.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
