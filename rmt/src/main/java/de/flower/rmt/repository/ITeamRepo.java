package de.flower.rmt.repository;

import de.flower.rmt.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author oblume
 */
public interface ITeamRepo extends JpaRepository<Team, Long> {

}
