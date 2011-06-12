package de.flower.rmt.service;

import de.flower.rmt.model.TeamBE;

import java.util.List;

/**
 * @author oblume
 */
public interface IMyTeamManager {

    void save(TeamBE entity);

    List<TeamBE> findAll();

    void delete(TeamBE modelObject);
}
