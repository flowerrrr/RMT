package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Player;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class ResponseManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testFindResponder() {
        Event event = testData.createEvent();
        List<Player> players = event.getTeam().getPlayers();
        assertTrue(invitationManager.findAllByEventAndStatus(event, RSVPStatus.ACCEPTED).isEmpty());
        Invitation invitation = responseManager.respond(event.getId(), players.get(0).getUser().getId(), RSVPStatus.ACCEPTED);
        List<Invitation> invitations = invitationManager.findAllByEventAndStatus(event, RSVPStatus.ACCEPTED);
        assertEquals(invitation, invitations.get(0));
        assertEquals((long) invitationManager.numByEventAndStatus(event, RSVPStatus.ACCEPTED), invitations.size());

        invitation = responseManager.respond(event.getId(), players.get(1).getUser().getId(), RSVPStatus.ACCEPTED);
        invitations = invitationManager.findAllByEventAndStatus(event, RSVPStatus.ACCEPTED);
        assertTrue(invitations.size() == 2);
        assertEquals((long) invitationManager.numByEventAndStatus(event, RSVPStatus.ACCEPTED), invitations.size());
    }

    @Test
    public void testRespond() {
        Event event = testData.createEvent();
        Player player = event.getTeam().getPlayers().get(0);
        RSVPStatus status = RSVPStatus.ACCEPTED;
        // first initial invitation
        Invitation invitation = responseManager.respond(event.getId(), player.getUser().getId(), status);
        assertEquals(invitation.getStatus(), status);

        // update invitation for player
        status = RSVPStatus.DECLINED;
        invitation = responseManager.respond(event.getId(), player.getUser().getId(), status);
        assertEquals(invitation.getStatus(), status);
    }
}