package de.flower.rmt.repository;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;

import java.util.List;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IResponseRepo extends IRepository<Response, Long> {

    List<Response> findByEventAndStatusOrderByDateAsc(Event event, RSVPStatus rsvpStatus);

    Response findByEventAndPlayer(Event event, Player player);
}
