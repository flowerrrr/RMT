package de.flower.rmt.service;

import de.flower.rmt.model.Team;

import java.util.List;

/**
 * @author oblume
 */
public interface ITeamManager {

    void save(Team entity);

    List<Team> findAll();

    void delete(Team modelObject);

    Team newTeamInstance();
}
