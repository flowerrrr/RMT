package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IEventRepo extends IRepository<Event, Long> {

    List<Event> findAllByClub(Club club);

    /**
     * Make join to table team to eager fetch this association. it is needed in the eventlistpanel.
     */
    @Query("select e from Event e left join FETCH e.team t  join e.invitations i where i.user = :user and e.date >= :date")
    List<Event> findAllUpcomingByInvitee(@Param("user") User user, @Param("date") Date date);
}
