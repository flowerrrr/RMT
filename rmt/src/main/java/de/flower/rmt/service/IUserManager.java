package de.flower.rmt.service;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author oblume
 */
public interface IUserManager {

    User findByUsername(String username);

    void save(User user);

    // List<User> findAll();

    void delete(User user);

    List<User> findAll(Attribute... attributes);

    /**
     * Returns all players of the club that are not assigned to the given team.
     *
     * @param team
     * @return
     */
    List<User> findUnassignedPlayers(Team team);

    User newUserInstance();
}
