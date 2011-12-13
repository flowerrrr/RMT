package de.flower.rmt.repository;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("select count(r) from Response r where r.event = :event and r.status = :status")
    Long numByEventAndStatus(@Param("event") Event event, @Param("status") RSVPStatus rsvpStatus);

    Response findByEventAndPlayer(Event event, Player player);

}
