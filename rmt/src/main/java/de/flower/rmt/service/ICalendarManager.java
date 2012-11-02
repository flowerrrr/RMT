package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.service.type.CalendarFilter;
import de.flower.rmt.ui.markup.html.calendar.CalEvent;
import org.joda.time.DateTime;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface ICalendarManager {

    CalItem loadById(Long id, Attribute... attributes);

    void save(CalItemDto dto, final User user);

    List<CalEvent> findAllByCalendarAndRange(List<CalendarFilter> calendarFilters, final DateTime start, final DateTime end);

    List<CalItem> findAllByUserAndRange(final User user, final DateTime calStart, final DateTime calEnd);

    void delete(Long id);

    List<CalendarFilter> getCalendarFilters();
}
