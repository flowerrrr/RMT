package de.flower.rmt.repository;

import de.flower.rmt.model.Invitee;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
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
public interface IInviteeRepo extends IRepository<Invitee, Long> {

    List<Invitee> findByEventAndStatusOrderByDateAsc(Event event, RSVPStatus rsvpStatus);

    @Query("select count(r) from Invitee r where r.event = :event and r.status = :status")
    Long numByEventAndStatus(@Param("event") Event event, @Param("status") RSVPStatus rsvpStatus);

    Invitee findByEventAndUser(Event event, User user);

    List<Invitee> findByEvent(Event event);
}
