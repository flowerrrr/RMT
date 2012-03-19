package de.flower.rmt.repository;

import de.flower.common.model.ObjectStatus;
import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IPlayerRepo extends IRepository<Player, Long> {

    // List<Player> findAllByTeam(Team team);

    Player findByTeamAndUserAndObjectStatusNot(Team team, User user, ObjectStatus objectStatus);

    // List<Player> findAllByUser(User user);
}
