package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IResponseManager {

    List<Response> findByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    Response respond(Event event, Player player, RSVPStatus status, String comment);
}
