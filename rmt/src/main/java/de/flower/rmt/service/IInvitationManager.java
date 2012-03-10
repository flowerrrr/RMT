package de.flower.rmt.service;

import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IInvitationManager {

    Invitation newInstance(Event event, User user);

    Invitation newInstance(Event event, String guestName);

    Invitation loadById(Long id);

    List<Invitation> findAllByEvent(Event event, final Attribute... attributes);

    List<Invitation> findAllByEventAndStatus(Event event, RSVPStatus rsvpStatus, Attribute... attributes);

    Long numByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    // List<Invitation> findAlByEmails(final Event event, List<String> addressList);

    Invitation loadByEventAndUser(Event event, User user);

    Invitation save(Invitation invitation);

    void delete(Invitation invitation);

    void markInvitationSent(Event event, List<String> addressList);
}