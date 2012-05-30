package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.event.Event;

import java.util.List;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IEventRepo extends IRepository<Event, Long> {

    List<Event> findAllByTeam(Team team);

}
