package de.flower.rmt.service;

import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.model.type.Notification;

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
     * Creates a new event an also creates invitations for all players of the
     * team of the event.
     * @param entity
     * @param createInvitations
     */
    void create(Event entity, boolean createInvitations);

    Event loadById(Long id, Attribute... attributes);

    List<Event> findAll(Attribute... attributes);

    List<Event> findAllUpcomingByUser(User user);

    void delete(Event entity);

    Event newInstance(EventType eventType);

    /**
     * Used for deep links. Loads event and checks if user has access rights to this event.
     * @param id
     * @param user
     * @return
     */
    Event loadByIdAndUser(Long id, User user);

    void sendInvitationMail(Long id, Notification notification);

    @Deprecated // experimental
    Event initAssociations(Event event, Attribute... attributes);
}
