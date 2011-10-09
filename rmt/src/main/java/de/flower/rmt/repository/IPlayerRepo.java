package de.flower.rmt.repository;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;
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
public interface IPlayerRepo extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player>, IPlayerRepoEx {

    List<Player> findByTeam(Team team);
}
