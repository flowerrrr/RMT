package de.flower.rmt.service;

import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IEventManager {

    void save(Event entity);

    Event loadById(Long id, Attribute... attributes);

    List<Event> findAll();

    List<Event> findUpcomingByUserPlayer(User user);

    void delete(Event entity);

    Event newInstance(EventType eventType);

    /**
     * Used for deep links. Loads event and checks if user has access rights to this event.
     * @param id
     * @param user
     * @return
     */
    Event loadByIdAndUser(Long id, User user);
}
