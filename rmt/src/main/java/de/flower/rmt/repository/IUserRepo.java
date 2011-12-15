package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IUserRepo extends IRepository<User, Long> {

    User findByEmail(String username);

    List<User> findByClub(Club club);

    @Query("select u from User u where u.club = :club and u not in (select u2 from Player p join p.user u2 where p.team = :team)")
    List<User> findUnassignedPlayers(@Param("team") Team team, @Param("club") Club club);

    @Query("select u from User u join u.players p where p.team = :team")
    List<User> findByTeam(@Param("team") Team team);
}
