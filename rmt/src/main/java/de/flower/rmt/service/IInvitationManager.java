package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;

import javax.mail.internet.InternetAddress;
import javax.persistence.metamodel.Attribute;
import java.util.Collection;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IInvitationManager {

    Invitation newInstance(Event event, User user);

    Invitation newInstance(Event event, String guestName);

    Invitation loadById(Long id, Attribute... attributes);

    List<Invitation> findAllByEvent(Event event, Attribute... attributes);

    List<Invitation> findAllByEventSortedByName(Event event);

    /**
     * Returns all invitees that have an email address (no guest players) and
     * that have email-notification turned on in their player settings.
     * @param event
     * @return
     */
    List<Invitation> findAllForNotificationByEventSortedByName(Event event);

    /**
     * Returns all invitees with an email address, also those with email notifications
     * turned off.
     * @param event
     * @return string for usage in href="mailto: ..."
     */
    List<InternetAddress> getAddressesForfAllInvitees(Event event);

    List<Invitation> findAllByEventAndStatus(Event event, RSVPStatus rsvpStatus, Attribute... attributes);

    Long numByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    List<Invitation> findAllForNoResponseReminder(Event event);

    // List<Invitation> findAlByEmails(final Event event, List<String> addressList);

    Invitation loadByEventAndUser(Event event, User user);

    Invitation findByEventAndUser(Event event, User user);

    void save(Invitation invitation);

    void delete(Long id);

    void markInvitationSent(Event event, List<String> addressList);

    void markNoResponseReminderSent(List<Invitation> invitations);

    /**
     * Create invitations for given users.
     *
     * @param entity
     * @param userIds
     */
    void addUsers(Event entity, Collection<Long> userIds);

    /**
     * Add guest player to event.
     */
    void addGuestPlayer(Event entity, String guestName);

}