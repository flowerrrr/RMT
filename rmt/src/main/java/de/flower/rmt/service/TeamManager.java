package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.Users;
import de.flower.rmt.repository.ITeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author oblume
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class TeamManager extends AbstractService implements ITeamManager {

    @Autowired
    private ITeamRepo teamRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Team entity) {
        teamRepo.save(entity);
    }

    @Override
    public List<Team> findAll() {
        return teamRepo.findAllByClub(getClub());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Team entity) {
        // TODO (oblume - 11.06.11) decide whether to soft or hard delete entity.
        teamRepo.delete(entity);
    }

    @Override
    public Team newTeamInstance() {
        return new Team(getClub());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addPlayer(Team team, Users player) {
        addPlayers(team, Arrays.asList(player));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addPlayers(Team team, List<Users> players) {
        team = teamRepo.reload(team);
        for (Users player : players) {
            Check.isTrue(!team.getPlayers().contains(player));
            team.getPlayers().add(player);
        }
        save(team);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removePlayer(Team team, Users player) {
        team = teamRepo.reload(team);
        Check.isTrue(team.getPlayers().contains(player));
        team.getPlayers().remove(player);
        save(team);
    }

    @Override
    public List<Users> getPlayers(Team team) {
        team = teamRepo.reload(team);
        List<Users> players = team.getPlayers();
        // init collection to avoid lazyinitexception
        players.size();
        return players;
    }
}
