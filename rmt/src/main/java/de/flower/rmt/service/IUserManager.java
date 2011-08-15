package de.flower.rmt.service;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.Users;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author oblume
 */
public interface IUserManager {

    Users findByUsername(String username);

    void save(Users user);

    // List<Users> findAll();

    void delete(Users user);

    List<Users> findAll(Attribute... attributes);

    /**
     * Returns all players of the club that are not assigned to the given team.
     *
     * @param team
     * @return
     */
    List<Users> findUnassignedPlayers(Team team);

    Users newPlayerInstance();
}
