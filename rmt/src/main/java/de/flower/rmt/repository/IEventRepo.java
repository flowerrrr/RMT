package de.flower.rmt.repository;

import de.flower.common.repository.IRepository;
import de.flower.rmt.model.Club;
import de.flower.rmt.model.event.Event;

import java.util.List;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author oblume
 */
public interface IEventRepo extends IRepository<Event, Long> {

    List<Event> findAllByClub(Club club);
}
