package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IResponseManager {

    Response newInstance(Event event);

    Response loadById(Long id);

    List<Response> findByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    Long numByEventAndStatus(Event event, RSVPStatus rsvpStatus);

    Response findByEventAndUser(Event event, User user);

    Response respond(Event event, Player player, RSVPStatus status, String comment);

    Response respond(Long eventId, Long userId, RSVPStatus status);

    Response save(Response response);

}