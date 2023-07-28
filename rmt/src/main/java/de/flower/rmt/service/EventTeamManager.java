package de.flower.rmt.service;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import de.flower.common.util.NameFinder;
import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.EventTeamPlayer;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Invitation_;
import de.flower.rmt.model.db.entity.QEventTeamPlayer;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.repository.IEventTeamPlayerRepo;
import de.flower.rmt.repository.IEventTeamRepo;
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
public class EventTeamManager extends AbstractService {

    @Autowired
    private IEventTeamRepo eventTeamRepo;

    @Autowired
    private IEventTeamPlayerRepo eventTeamPlayerRepo;

    @Autowired
    private InvitationManager invitationManager;

    public List<EventTeam> findTeamsOrderByRank(final Event event) {
        return eventTeamRepo.findByEventOrderByRankAsc(event);
    }

    public List<EventTeamPlayer> findEventTeamPlayers(final EventTeam eventTeam, final Path<?>... attributes) {
        BooleanExpression isEventTeam = QEventTeamPlayer.eventTeamPlayer.eventTeam.eq(eventTeam);
        List<EventTeamPlayer> items = eventTeamPlayerRepo.findAll(isEventTeam, new OrderSpecifier(Order.ASC, QEventTeamPlayer.eventTeamPlayer.order), attributes);
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].equals(QEventTeamPlayer.eventTeamPlayer.invitation)) {
                // load invitations with users as we need them later. fetching user name via attributes is not possible
                // once loaded into session we avoid LIE
                invitationManager.findAllByEvent(eventTeam.getEvent(), Invitation_.user);
            }
        }
        return items;
    }

    public List<Invitation> findInvitationsInEventTeams(final Event event) {
        BooleanExpression isEvent = QEventTeamPlayer.eventTeamPlayer.eventTeam.event.eq(event);
        List<EventTeamPlayer> items = eventTeamPlayerRepo.findAll(isEvent, QEventTeamPlayer.eventTeamPlayer.invitation);
        List<Invitation> invitations = Lists.transform(items, new Function<EventTeamPlayer, Invitation>() {
            @Override
            public Invitation apply(final EventTeamPlayer item) {
                return item.getInvitation();
            }
        });

        return invitations;
    }

    @Transactional(readOnly = false)
    public EventTeam addTeam(final Event event) {
        EventTeam entity = new EventTeam(event);
        String name = NameFinder.newName("Team", "A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P".split("\\."), new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                return eventTeamRepo.findByEventAndName(event, input) == null;
            }
        });
        entity.setName(name);
        // entity.setRank(0); // add at end of ranked teams
        eventTeamRepo.save(entity);
        return entity;
    }

    public List<EventTeam> findTeams(final Event event) {
        return eventTeamRepo.findByEvent(event);
    }

    @Transactional(readOnly = false)
    public void removeTeam(final EventTeam eventTeam) {
        eventTeamRepo.delete(eventTeam);
    }

    @Transactional(readOnly = false)
    public void addPlayer(final Long eventTeamId, final Long invitationId, final Long insertBeforePlayerId) {
        EventTeam eventTeam = eventTeamRepo.findOne(eventTeamId);
        Invitation invitation = invitationManager.loadById(invitationId);
        // player already assigned to a team?
        EventTeamPlayer player = eventTeamPlayerRepo.findByInvitation(invitation);
        EventTeamPlayer insertBeforePlayer = null;
        if (insertBeforePlayerId != null) {
            insertBeforePlayer = eventTeamPlayerRepo.findOne(insertBeforePlayerId);
        }
        if (player != null) {
            // is already player of given team
            EventTeam oldTeam = player.getEventTeam();
            if (oldTeam.equals(eventTeam)) {
                if (player.equals(insertBeforePlayer)) {
                    // player dropped on itself - nothing to do
                } else {
                    // resort player
                    eventTeam.removePlayer(player);
                    eventTeam.addPlayer(player, insertBeforePlayer);
                    eventTeamPlayerRepo.save(player);
                    eventTeamRepo.save(eventTeam);
                }
            } else {
                // re-order oldTeam. close gap that player leaves when removed from the team
                oldTeam.removePlayer(player);
                // assign new team
                player.setEventTeam(eventTeam);
                // add to end of list
                eventTeam.addPlayer(player, insertBeforePlayer);
                eventTeamPlayerRepo.save(player);
                eventTeamRepo.save(eventTeam);
            }
        } else {
            EventTeamPlayer entity = new EventTeamPlayer(eventTeam, invitation);
            eventTeam.addPlayer(entity, insertBeforePlayer);
            eventTeamPlayerRepo.save(entity);
            eventTeamRepo.save(eventTeam);
        }
    }

    @Transactional(readOnly = false)
    public void removePlayer(final Long playerId) {
        EventTeamPlayer player = eventTeamPlayerRepo.findOne(playerId);
        player.getEventTeam().removePlayer(player);
        eventTeamRepo.save(player.getEventTeam());
        eventTeamPlayerRepo.delete(playerId);
    }

    @Transactional(readOnly = false)
    public void removeInvitation(final Long invitationId) {
        Invitation invitation = invitationManager.loadById(invitationId);
        EventTeamPlayer player = eventTeamPlayerRepo.findByInvitation(invitation);
        if (player != null) {
            removePlayer(player.getId());
        }
    }

    public void save(final EventTeam eventTeam) {
        validate(eventTeam);
        eventTeamRepo.save(eventTeam);
    }

    @Transactional(readOnly = false)
    public void setRank(final Long eventTeamId, final EventTeam insertBeforeEventTeam) {
        EventTeam eventTeam = eventTeamRepo.findOne(eventTeamId);
        // get all teams ordered by rank
        List<EventTeam> teams = findTeamsOrderByRank(eventTeam.getEvent());
        if (eventTeam.equals(insertBeforeEventTeam)) {
            // dropped on itself -> do nothing
        } else {
            // remove team and then insert at specified position
            teams.remove(eventTeam);
            teams.add(teams.indexOf(insertBeforeEventTeam), eventTeam);
        }
        // now iterate of teams and set Rank according to index
        for (EventTeam team : teams) {
            team.setRank(teams.indexOf(team) + 1);
            eventTeamRepo.save(team);
        }
    }
}
