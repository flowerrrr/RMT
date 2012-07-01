package de.flower.rmt.service;

import com.mysema.query.types.Path;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;

import javax.mail.internet.InternetAddress;
import javax.persistence.metamodel.Attribute;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IInvitationManager {

    Invitation newInstance(Event event, User user);

    Invitation newInstance(Event event, String guestName);

    Invitation loadById(Long id, Attribute... attributes);

    List<Invitation> findAllByEvent(Event entity, Attribute... attributes);

    List<Invitation> findAllByEventSortedByName(Event event, Attribute... attributes);

    List<Invitation> findAllByEventAndStatusSortedByName(Event event, RSVPStatus status, Attribute... attributes);

    /**
     * Returns all invitees that have an email address (no guest players) and
     * that have email-notification turned on in their player settings.
     *
     * @param event
     * @return
     */
    List<Invitation> findAllForNotificationByEventSortedByName(Event event);

    /**
     * Returns all invitees with an email address, also those with email notifications
     * turned off.
     *
     * @param event
     * @return string for usage in href="mailto: ..."
     */
    List<InternetAddress> getAddressesForfAllInvitees(Event event);

    List<Invitation> findAllByEventAndStatus(Event event, RSVPStatus rsvpStatus, Attribute... attributes);

    Long numByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    List<Invitation> findAllForNoResponseReminder(Event event, final int hoursAfterInvitationSent);

    List<Invitation> findAllForUnsureReminder(Event event);

    // List<Invitation> findAlByEmails(final Event event, List<String> addressList);

    Invitation loadByEventAndUser(Event event, User user);

    Invitation findByEventAndUser(Event event, User user, Path<?>... attributes);

    void save(Invitation invitation);

    /**
     * @param invitation
     * @param comment    sets/overrides the first comment of the author (current security context user)
     */
    void save(Invitation invitation, String comment);

    void delete(Long id);

    /**
     * @param event
     * @param addressList list of email-addresses (matched against user-email, not secondary email)
     * @param date        visible for testing, if null will be replaced with current date
     */
    void markInvitationSent(Event event, List<String> addressList, Date date);

    void markNoResponseReminderSent(List<Invitation> invitations);

    void markUnsureReminderSent(List<Invitation> invitations);

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