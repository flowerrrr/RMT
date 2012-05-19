package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import org.springframework.data.jpa.repository.Modifying;
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
public interface IInvitationRepo extends IRepository<Invitation, Long> {

    // List<Invitation> findAllByEventAndStatusOrderByDateAsc(Event event, RSVPStatus rsvpStatus);

    @Query("select count(r) from Invitation r where r.event = :event and r.status = :status")
    Long numByEventAndStatus(@Param("event") Event event, @Param("status") RSVPStatus rsvpStatus);

    Invitation findByEventAndUser(Event event, User user);

    // List<Invitation> findAllByEvent(Event event);

    @Modifying
    @Query("update Invitation i set i.invitationSent = true where i.event = :event and i.user in (select u from User u where u.email in (:addressList))")
    void markInvitationSent(@Param("event") Event event, @Param("addressList") List<String> addressList);

}
