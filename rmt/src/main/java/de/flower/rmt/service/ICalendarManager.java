package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.dto.CalItemDto;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface ICalendarManager {

    CalItem newInstance();

    CalItem loadById(Long id);

    void save(CalItemDto dto);

    List<CalItem> findAllByUser(User object);

}
