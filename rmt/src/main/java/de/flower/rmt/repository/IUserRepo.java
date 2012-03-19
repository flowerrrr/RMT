package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IUserRepo extends IRepository<User, Long> {

    User findByEmail(String username);

    @Query("select u from User u where u.objectStatus != 1 and u.club = :club and u not in (select u2 from Player p join p.user u2 where p.team = :team)")
    List<User> findAllUnassignedPlayers(@Param("team") Team team, @Param("club") Club club);

    @Query("select u from User u where u.objectStatus != 1 and u.club = :club and u not in (select u2 from Invitation i join i.user u2 where i.event = :event)")
    List<User> findAllUninvitedPlayers(@Param("event") Event event, @Param("club") Club club);

//    @Query("select u from User u join u.players p where p.team = :team")
//    List<User> findAllByTeam(@Param("team") Team team);

}
