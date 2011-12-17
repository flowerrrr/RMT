package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IPlayerManager {

    List<Player> findAllByTeam(Team team, Attribute... attributes);

    List<Player> findAllByUser(User user);

    Player findByTeamAndUser(Team team, User user);

    Player findByEventAndUser(Event event, User user);

    void save(Player entity);
}
