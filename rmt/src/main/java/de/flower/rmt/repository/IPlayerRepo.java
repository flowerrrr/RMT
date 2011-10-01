package de.flower.rmt.repository;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;

import java.util.List;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IPlayerRepo extends IRepository<Player, Long> {

    List<Player> findByTeam(Team team);

}