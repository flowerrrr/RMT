package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.*;
import de.flower.rmt.model.db.entity.event.Event;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static de.flower.rmt.repository.Specs.*;
import static org.springframework.data.jpa.domain.Specifications.where;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PlayerManager extends AbstractService {

    @Autowired
    private IPlayerRepo playerRepo;

    @Autowired
    private IEventRepo eventRepo;

    @Autowired
    private ITeamRepo teamRepo;

    @Autowired
    private IUserRepo userRepo;

    public List<Player> findAllByTeam(Team team) {
        return playerRepo.findAll(where(eq(Player_.team, team)).and(orderByJoin(Player_.user, User_.fullname, true)));
    }

    public List<Player> findAllByUser(User user, Attribute... attributes) {
        return playerRepo.findAll(where(eq(Player_.user, user)).and(fetch(attributes)));
    }

    @Deprecated // not used
    public Player findByTeamAndUser(final Team team, final User user) {
        Specifications spec = where(eq(Player_.team, team)).and(eq(Player_.user, user));
        return playerRepo.findOne(spec);
    }

    @Deprecated // not used
    public Player findByEventAndUser(final Event event, final User user) {
        eventRepo.reattach(event);
        return findByTeamAndUser(event.getTeam(), user);
    }

    public void save(final Player entity) {
        validate(entity);
        playerRepo.save(entity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addPlayer(Team team, User user) {
        addPlayers(team, Arrays.asList(user));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addPlayers(Team team, List<User> users) {
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

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removePlayer(Team team, Player player) {
        teamRepo.reattach(team);
        Check.isTrue(team.getPlayers().contains(player));
        delete(player);
    }

    public void delete(Player player) {
        playerRepo.delete(player);
    }

    public void removeUserFromAllTeams(final User user) {
        List<Player> list = findAllByUser(user);
        for (Player player : list) {
            delete(player);
        }
    }

    @Transactional(readOnly = false)
    public void deleteByTeam(Team team) {
        teamRepo.reattach(team);
        for (Player player : team.getPlayers()) {
            // can do hard delete cause player is not referenced from other entities than team and user.
            // in case recovery is needed just re-add the users to the undeleted team.
            delete(player);
        }
    }

    public List<Player> sortByTeam(List<Player> list) {
        // return sorted by team name
        Collections.sort(list, new Comparator<Player>() {
            @Override
            public int compare(final Player o1, final Player o2) {
                return o1.getTeam().getName().compareTo(o2.getTeam().getName());
            }
        });
        return list;
    }
}
