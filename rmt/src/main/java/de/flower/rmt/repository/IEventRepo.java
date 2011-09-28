package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IEventRepo extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAllByClub(Club club);
}
