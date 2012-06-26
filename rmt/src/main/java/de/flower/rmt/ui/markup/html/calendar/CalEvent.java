package de.flower.rmt.ui.markup.html.calendar;

import java.util.Date;

/**
* @author flowerrrr
*/
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


}
