package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.repository.IPlayerRepo;
import de.flower.rmt.repository.ITeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class TeamManager extends AbstractService implements ITeamManager {

    @Autowired
    private ITeamRepo teamRepo;

    @Autowired
    private IPlayerRepo playerRepo;

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
        // TODO (flowerrrr - 11.06.11) decide whether to soft or hard delete entity.
        teamRepo.delete(entity);
    }

    @Override
    public Team newTeamInstance() {
        return new Team(getClub());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addPlayer(Team team, User user) {
        addPlayers(team, Arrays.asList(user));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addPlayers(Team team, List<User> users) {
        team = teamRepo.reload(team);
        for (User user : users) {
            Player player = new Player(team, user);
            playerRepo.save(player);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removePlayer(Team team, Player player) {
        team = teamRepo.reload(team);
        Check.isTrue(team.getPlayers().contains(player));
        deletePlayer(player);
    }

    @Override
    public List<Player> getPlayers(Team team) {
        team = teamRepo.reload(team);
        List<Player> players = team.getPlayers();
        // init collection to avoid lazyinitexception
        players.size();
        return players;
    }

    public void deletePlayer(Player player) {
        playerRepo.delete(player);
    }
}
