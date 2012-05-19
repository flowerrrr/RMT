package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IUserRepo extends IRepository<User, Long> {

    User findByFullname(String candidate);

    User findByEmail(String username);

    @Query("select u from User u where u.objectStatus <> 1 and u.club = :club and u not in (select u2 from Player p join p.user u2 where p.team = :team) order by u.fullname asc")
    List<User> findAllUnassignedPlayers(@Param("team") Team team, @Param("club") Club club);

    @Query("select u from User u where u.objectStatus <> 1 and u.club = :club and u not in (select u2 from Invitation i join i.user u2 where i.event = :event) order by u.fullname asc")
    List<User> findAllUninvitedPlayers(@Param("event") Event event, @Param("club") Club club);

//    @Query("select u from User u join u.players p where p.team = :team")
//    List<User> findAllByTeam(@Param("team") Team team);

    /**
     * Was too stupid to build this query with criteria api
     */
    @Query("select distinct u from User u join fetch u.roles left join fetch u.players p left join fetch p.team where u.objectStatus <> 1 and u.club = :club order by u.fullname")
    List<User> findAllFetchTeams(@Param("club") Club club);

}
