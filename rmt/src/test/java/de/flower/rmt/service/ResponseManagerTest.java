package de.flower.rmt.service;

import de.flower.rmt.model.Invitee;
import de.flower.rmt.model.Player;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class ResponseManagerTest extends AbstractIntegrationTests {

    @Test
    public void testFindResponder() {
        Event event = testData.createEventWithoutResponses();
        List<Player> players = event.getTeam().getPlayers();
        assertTrue(inviteeManager.findByEventAndStatus(event, RSVPStatus.ACCEPTED).isEmpty());
        Invitee invitee = responseManager.respond(event, players.get(0).getUser(), RSVPStatus.ACCEPTED, "some comment");
        List<Invitee> invitees = inviteeManager.findByEventAndStatus(event, RSVPStatus.ACCEPTED);
        assertEquals(invitee, invitees.get(0));
        assertEquals((long) inviteeManager.numByEventAndStatus(event, RSVPStatus.ACCEPTED), invitees.size());

        invitee = responseManager.respond(event, players.get(1).getUser(), RSVPStatus.ACCEPTED, "some comment");
        invitees = inviteeManager.findByEventAndStatus(event, RSVPStatus.ACCEPTED);
        assertTrue(invitees.size() == 2);
        assertEquals((long) inviteeManager.numByEventAndStatus(event, RSVPStatus.ACCEPTED), invitees.size());
    }

    @Test
    public void testRespond() {
        Event event = testData.createEventWithoutResponses();
        Player player = event.getTeam().getPlayers().get(0);
        String comment = "Comment #1";
        RSVPStatus status = RSVPStatus.ACCEPTED;
        // first initial invitee
        Invitee invitee = responseManager.respond(event, player.getUser(), status, comment);
        assertEquals(invitee.getStatus(), status);
        assertEquals(invitee.getComment(), comment);

        // update invitee for player
        comment = "Comment #2";
        status = RSVPStatus.DECLINED;
        invitee = responseManager.respond(event, player.getUser(), status, comment);
        assertEquals(invitee.getStatus(), status);
        assertEquals(invitee.getComment(), comment);
    }
}