package de.flower.rmt.repository;

import de.flower.rmt.model.MyTeamBE;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author oblume
 */
public interface IMyTeamRepo extends JpaRepository<MyTeamBE, Long> {

}
