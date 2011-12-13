package de.flower.rmt.repository;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    Player findByTeamAndUser(Team team, User user);

    @Query("select p from Player p where p.team = :team and p.optional = false and p not in (select r.player from Response r join r.player where r.event = :event)")
    List<Player> findNotResponded(@Param("event") Event event, @Param("team") Team team);

    @Query("select count(p) from Player p where p.team = :team and p.optional = false and p not in (select r.player from Response r join r.player where r.event = :event)")
    Long numNotResponded(@Param("event") Event event, @Param("team") Team team);
}
