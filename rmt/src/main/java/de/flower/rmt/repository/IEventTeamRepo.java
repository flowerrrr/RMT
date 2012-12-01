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

    List<EventTeam> findByEvent(Event event);

    EventTeam findByName(String name);
}
