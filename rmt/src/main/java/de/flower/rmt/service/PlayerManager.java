package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IEventRepo;
import de.flower.rmt.repository.IPlayerRepo;
import de.flower.rmt.repository.ITeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PlayerManager extends AbstractService implements IPlayerManager {

    @Autowired
    private IPlayerRepo playerRepo;

    @Autowired
    private ITeamRepo teamRepo;

    @Autowired
    private IEventRepo eventRepo;


    @Override
    @Transactional
    public List<Player> findNotResponded(Event event) {
        return playerRepo.findNotResponded(event, event.getTeam());
    }

    @Override
    public Long numNotResponded(final Event event) {
        return playerRepo.numNotResponded(event, event.getTeam());
    }

    /**
     * Shitty implementation using jpa association to retrieve list of teams.
     * @param team
     * @return
     */
    @Override
    public List<Player> findByTeam(Team team) {
        return playerRepo.findByTeam(team);
    }

    @Override
    public Player findByTeamAndUser(final Team team, final User user) {
        return playerRepo.findByTeamAndUser(team, user);
    }

    @Override
    public Player findByEventAndUser(final Event event, final User user) {
        eventRepo.reattach(event);
        return findByTeamAndUser(event.getTeam(), user);
    }
}
