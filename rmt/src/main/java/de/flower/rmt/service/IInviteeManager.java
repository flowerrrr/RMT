package de.flower.rmt.service;

import de.flower.rmt.model.Invitee;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IInviteeManager {

    Invitee newInstance(Event event, User user);

    Invitee newInstance(Event event, String guestName);

    Invitee loadById(Long id);

    List<Invitee> findByEvent(Event event);

    List<Invitee> findByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    Long numByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    Invitee loadByEventAndUser(Event event, User user);

    Invitee save(Invitee invitee);

}