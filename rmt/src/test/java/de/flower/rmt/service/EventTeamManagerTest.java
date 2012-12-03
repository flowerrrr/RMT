package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.EventTeamPlayer;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class EventTeamManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testAddPlayer() {
        Event event = testData.createEvent();
        Invitation invitation = invitationManager.findAllByEvent(event).get(0);
        EventTeam eventTeam = eventTeamManager.addTeam(event);
        eventTeamManager.addPlayer(eventTeam.getId(), invitation.getId(), null);
        List<EventTeamPlayer> players = eventTeamManager.findEventTeamPlayers(eventTeam);
        assertEquals(players.size(), 1);
    }
}
