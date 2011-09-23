package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;

import java.util.List;

/**
 * @author oblume
 */
public interface IUserRepoEx {

    List<User> findUnassignedPlayers(Team team, Club club);

}
