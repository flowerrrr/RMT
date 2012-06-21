package de.flower.rmt.service;

import de.flower.common.ui.calendar.CalEvent;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.type.CalendarType;
import de.flower.rmt.model.dto.CalItemDto;
import org.joda.time.DateTime;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface ICalendarManager {

    CalItem loadById(Long id, Attribute... attributes);

    void save(CalItemDto dto);

    List<CalEvent> findAllByCalendarAndRange(List<CalendarType> calendarTypes, final DateTime start, final DateTime end);

    List<CalItem> findAllByUserAndRange(final User user, final DateTime calStart, final DateTime calEnd);

    void delete(Long id);
}
