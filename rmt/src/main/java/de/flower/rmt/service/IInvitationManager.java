package de.flower.rmt.service;

import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IInvitationManager {

    Invitation newInstance(Event event, User user);

    Invitation newInstance(Event event, String guestName);

    Invitation loadById(Long id);

    List<Invitation> findByEvent(Event event);

    List<Invitation> findByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    Long numByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    Invitation loadByEventAndUser(Event event, User user);

    Invitation save(Invitation invitation);

}