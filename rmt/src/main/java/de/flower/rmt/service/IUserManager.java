package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.dto.Password;

import javax.mail.internet.InternetAddress;
import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IUserManager {

    User loadById(Long id, final Attribute... attributes);

    User findByUsername(String username, final Attribute... attributes);

    void save(User user);

    /**
     * Saves user and assigns or removes manager role.
     * User object passed into this method should be
     * refreshed after this method call to reflect changes.
     * @param user
     * @param isManager
     */
    void save(User user, boolean isManager);

    void delete(Long id);

    List<User> findAll(Attribute... attributes);

    List<User> findAllFetchTeams(final Attribute... attributes);

    List<User> findAllByTeam(Team team);

    /**
     * Returns all players of the club that are not assigned to the given team.
     *
     * @param team
     * @return
     */
    List<User> findAllUnassignedPlayers(Team team);

    /**
     * Returns all players of the club that are not invited to the given event.
     *
     * @param event
     * @return
     */
    List<User> findAllUninvitedPlayers(Event event);

    /**
     * Returns all players of the given team that are not invited to the given event.
     * @param event
     * @param team
     * @return
     */
    List<User> findAllUninvitedPlayersByTeam(Event event, Team team);

    /**
     * Initialized the password fields with a
     * fresh password.
     *
     * @return
     */
    User newInstance();

    void resetPassword(User user, final boolean sendMail);

    void updatePassword(Long userId, Password password);

    void sendInvitation(Long userId);

    List<InternetAddress> getAddressesForfAllUsers();

    void onLoginSuccess(User user);
}
