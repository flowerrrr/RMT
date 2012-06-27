package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.event.Event;

/**
 * @author flowerrrr
 */
public interface IICalendarProvider {

    /**
     * Returns ics-ICalendar file (as string).
     * @param event
     */
    String getICalendar(Event event);

}
