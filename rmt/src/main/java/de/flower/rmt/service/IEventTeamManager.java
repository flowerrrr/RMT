package de.flower.rmt.service;

import com.mysema.query.types.Path;
import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.EventTeamPlayer;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IEventTeamManager {

    EventTeam addTeam(Event event);

    List<EventTeam> findTeams(Event event);

    List<EventTeamPlayer> findEventTeamPlayers(EventTeam eventTeam, Path<?>... attributes);

    List<Invitation> findInvitationsInEventTeams(Event event);

    void removeTeam(EventTeam eventTeam);

    void addPlayer(Long eventTeamId, Long invitationId, final Long insertBeforePlayerId);

    void removePlayer(Long playerId);

    void removeInvitation(Long invitationId);

    void save(EventTeam eventTeam);
}
