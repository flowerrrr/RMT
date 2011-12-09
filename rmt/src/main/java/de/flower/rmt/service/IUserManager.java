package de.flower.rmt.service;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.service.type.Password;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IUserManager {

    User loadById(Long id);

    User findByUsername(String username);

    void save(User user);

    void delete(User user);

    List<User> findAll(Attribute... attributes);

    /**
     * Returns all players of the club that are not assigned to the given team.
     *
     * @param team
     * @return
     */
    List<User> findUnassignedPlayers(Team team);

    /**
     * Initialized the password fields with a
     * fresh password.
     *
     * @return
     */
    User newInstance();

    void resetPassword(User user);

    void updatePassword(Long userId, Password password);
}
