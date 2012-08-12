package de.flower.rmt.service;

import com.mysema.query.types.EntityPath;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.model.dto.Notification;
import org.joda.time.DateTime;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IEventManager {

    /**
     * Saves or updates an event.
     *
     * @param entity
     */
    void save(Event entity);

    /**
     * Creates a new event an also creates invitations for all players of the
     * team of the event.
     *
     * @param entity
     * @param createInvitations
     */
    void create(Event entity, boolean createInvitations);

    Event loadById(Long id, Attribute... attributes);

    long getNumEventsByUser(final User user);

    List<Event> findAll(Attribute... attributes);

    List<Event> findAll(int page, int size, final User user, EntityPath<?>... attributes);

    Event findNextEvent(User user);

    List<Event> findAllByDateRange(DateTime start, DateTime end, EntityPath<?>... attributes);

    List<Event> findAllByDateRangeAndUser(DateTime start, DateTime end, final User user, EntityPath<?>... attributes);

    /**
     * Returns all events scheduled inside the next <code>hours</code>.
     * Calculated against Event.date - Event.time (not kickOff).
     * Does not return canceled events.
     */
    List<Event> findAllNextNHours(int hours);

    /**
     * Hard deletes an event and all invitations.
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Soft deletes all events of the team.
     * Soft in case deletion of team was human error to be able to recover.
     *
     * @param entity
     */
    void deleteByTeam(Team entity);

    Event newInstance(EventType eventType);

    void sendInvitationMail(Long id, Notification notification);

    boolean isEventClosed(Event event);

    void cancelEvent(Long id);
}
