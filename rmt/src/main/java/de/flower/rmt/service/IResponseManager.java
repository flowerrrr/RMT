package de.flower.rmt.service;

import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;

/**
 * @author flowerrrr
 */
// TODO (flowerrrr - 31.03.12) move to IInvitationManager?
public interface IResponseManager {

    Invitation respond(Event event, User user, RSVPStatus status, String comment);

    Invitation respond(Long eventId, Long userId, RSVPStatus status);

}