package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface ITeamManager {

    void save(Team entity);

    List<Team> findAll();

    void delete(Team entity);

    Team newTeamInstance();

    void addPlayer(Team team, User user);

    void addPlayers(Team team, List<User> users);

    void removePlayer(Team team, Player player);

    List<Player> getPlayers(Team team);

}
