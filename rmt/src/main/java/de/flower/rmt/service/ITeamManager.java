package de.flower.rmt.service;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface ITeamManager {

    void save(Team entity);

    Team loadById(Long id);

    List<Team> findAll();

    List<Team> findAllByUserPlayer(User user);

    void delete(Long id);

    Team newInstance();
}
