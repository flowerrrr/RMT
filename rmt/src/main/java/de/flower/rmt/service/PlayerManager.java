package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.Player_;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IEventRepo;
import de.flower.rmt.repository.IPlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import java.util.List;

import static de.flower.rmt.repository.Specs.eq;
import static de.flower.rmt.repository.Specs.fetch;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PlayerManager extends AbstractService implements IPlayerManager {

    @Autowired
    private IPlayerRepo playerRepo;

    @Autowired
    private IEventRepo eventRepo;


    @Override
    public List<Player> findAllByTeam(Team team, Attribute... attributes) {
        return playerRepo.findAll(where(eq(Player_.team, team)).and(fetch(attributes)));
    }

    @Override
    public List<Player> findAllByUser(User user, Attribute... attributes) {
        return playerRepo.findAll(where(eq(Player_.user, user)).and(fetch(attributes)));
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

    @Override
    public void save(final Player entity) {
        validate(entity);
        playerRepo.save(entity);
    }
}
