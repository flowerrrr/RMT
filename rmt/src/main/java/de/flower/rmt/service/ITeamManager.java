package de.flower.rmt.service;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.Users;

import java.util.List;

/**
 * @author oblume
 */
public interface ITeamManager {

    void save(Team entity);

    List<Team> findAll();

    void delete(Team modelObject);

    Team newTeamInstance();

    void addPlayer(Team team, Users player);

    void addPlayers(Team team, List<Users> players);

    void removePlayer(Team team, Users player);

    List<Users> getPlayers(Team team);

}
