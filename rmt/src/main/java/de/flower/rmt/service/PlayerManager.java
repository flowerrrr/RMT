package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.*;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IEventRepo;
import de.flower.rmt.repository.IPlayerRepo;
import de.flower.rmt.repository.ITeamRepo;
import de.flower.rmt.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import java.util.Arrays;
import java.util.List;

import static de.flower.rmt.repository.Specs.*;
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

    @Autowired
    private ITeamRepo teamRepo;

    @Autowired
    private IUserRepo userRepo;


    @Override
    public List<Player> findAllByTeam(Team team) {
        return playerRepo.findAll(where(eq(Player_.team, team)).and(orderByJoin(Player_.user, User_.fullname, true)));
    }

    @Override
    public List<Player> findAllByUser(User user, Attribute... attributes) {
        return playerRepo.findAll(where(eq(Player_.user, user)).and(fetch(attributes)));
    }

    @Override
    @Deprecated // not used
    public Player findByTeamAndUser(final Team team, final User user) {
        Specifications spec = where(eq(Player_.team, team)).and(eq(Player_.user, user));
        return playerRepo.findOne(spec);
    }

    @Override
    @Deprecated // not used
    public Player findByEventAndUser(final Event event, final User user) {
        eventRepo.reattach(event);
        return findByTeamAndUser(event.getTeam(), user);
    }

    @Override
    public void save(final Player entity) {
        validate(entity);
        playerRepo.save(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addPlayer(Team team, User user) {
        addPlayers(team, Arrays.asList(user));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addPlayers(Team team, List<User> users) {
        // TODO (flowerrrr - 22.03.12) use ids as parameters
        teamRepo.reattach(team);
        for (User user : users) {
            userRepo.reattach(user);
            Player player = new Player(team, user);
            player.setOptional(false);
            player.setNotification(true);
            player.setRetired(false);
            team.getPlayers().add(player);
            user.getPlayers().add(player);
            playerRepo.save(player);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removePlayer(Team team, Player player) {
        teamRepo.reattach(team);
        Check.isTrue(team.getPlayers().contains(player));
        delete(player);
    }

    public void delete(Player player) {
        playerRepo.delete(player);
    }

    @Override
    public void removeUserFromAllTeams(final User user) {
        List<Player> list = findAllByUser(user);
        for (Player player : list) {
            delete(player);
        }
    }


}
