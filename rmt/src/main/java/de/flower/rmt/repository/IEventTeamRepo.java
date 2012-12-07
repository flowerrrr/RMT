package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.event.Event;

import java.util.List;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IEventTeamRepo extends IRepository<EventTeam, Long> {

    /**
     * Ordered by date of creation.
     */
    List<EventTeam> findByEvent(Event event);

    List<EventTeam> findByEventOrderByRankAsc(Event event);

    EventTeam findByEventAndName(final Event event, String name);

}
