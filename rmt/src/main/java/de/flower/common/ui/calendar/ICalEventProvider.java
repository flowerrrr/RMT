package de.flower.common.ui.calendar;

import org.joda.time.DateTime;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface ICalEventProvider {

    List<CalEvent> loadCalEvents(DateTime start, DateTime end);

}
