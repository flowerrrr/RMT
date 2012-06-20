package de.flower.rmt.service;

import de.flower.common.ui.calendar.CalEvent;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.type.CalendarType;
import de.flower.rmt.model.dto.CalItemDto;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface ICalendarManager {

    CalItem loadById(Long id);

    void save(CalItemDto dto);

    List<CalEvent> findAllByCalendarAndRange(List<CalendarType> calendarTypes, final DateTime start, final DateTime end);

}
