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

    /**
     * Saves or updates an event.
     * @param entity
     */
    void save(Event entity);

    /**
     * Creates a new event an also creates invitees for all players of the
     * team of the event.
     * @param entity
     * @param createInvitees
     */
    void create(Event entity, boolean createInvitees);

    Event loadById(Long id, Attribute... attributes);

    List<Event> findAll();

    List<Event> findUpcomingByUser(User user);

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
