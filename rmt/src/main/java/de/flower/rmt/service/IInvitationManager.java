package de.flower.rmt.service;

import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;

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

    Invitation loadById(Long id);

    @Deprecated
    List<Invitation> findAllByEvent(Event event, final Attribute... attributes);

    List<Invitation> findAllByEventSortedByName(Event event);

    /**
     * Returns all invitees that have an email address (no guest players) and
     * that have email-notification turned on in their player settings.
     * @param event
     * @return
     */
    List<Invitation> findAllForNotificationByEventSortedByName(Event event);

    List<Invitation> findAllByEventAndStatus(Event event, RSVPStatus rsvpStatus, Attribute... attributes);

    Long numByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    // List<Invitation> findAlByEmails(final Event event, List<String> addressList);

    Invitation loadByEventAndUser(Event event, User user);

    void save(Invitation invitation);

    void delete(Long id);

    void markInvitationSent(Event event, List<String> addressList);

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

    List<InternetAddress[]> getAllInternetAddressesByEvent(Event event);
}