package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.Response;
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
    public void testFindNotResponded() {
        Event event = testData.createEvent();
        List<Player> players = event.getTeam().getPlayers();
        assertTrue(!players.isEmpty());
        List<Player> notResponders = playerManager.findNotResponded(event);
        assertEquals(notResponders, players);
        assertEquals((long) playerManager.numNotResponded(event), notResponders.size());

        responseManager.respond(event, players.get(0), RSVPStatus.ACCEPTED, "some comment");
        notResponders = playerManager.findNotResponded(event);
        assertEquals(players.size(), notResponders.size() + 1);
        assertEquals((long) playerManager.numNotResponded(event), notResponders.size());
    }

    @Test
    public void testFindResponder() {
        Event event = testData.createEvent();
        List<Player> players = event.getTeam().getPlayers();
        assertTrue(responseManager.findByEventAndStatus(event, RSVPStatus.ACCEPTED).isEmpty());
        Response response = responseManager.respond(event, players.get(0), RSVPStatus.ACCEPTED, "some comment");
        List<Response> responses = responseManager.findByEventAndStatus(event, RSVPStatus.ACCEPTED);
        assertEquals(response, responses.get(0));
        assertEquals((long) responseManager.numByEventAndStatus(event, RSVPStatus.ACCEPTED), responses.size());

        response = responseManager.respond(event, players.get(1), RSVPStatus.ACCEPTED, "some comment");
        responses = responseManager.findByEventAndStatus(event, RSVPStatus.ACCEPTED);
        assertTrue(responses.size() == 2);
        assertEquals((long) responseManager.numByEventAndStatus(event, RSVPStatus.ACCEPTED), responses.size());
    }

    @Test
    public void testRespond() {
        Event event = testData.createEvent();
        Player player = event.getTeam().getPlayers().get(0);
        String comment = "Comment #1";
        RSVPStatus status = RSVPStatus.ACCEPTED;
        // first initial response
        Response response = responseManager.respond(event, player, status, comment);
        assertEquals(response.getStatus(), status);
        assertEquals(response.getComment(), comment);

        // update response for player
        comment = "Comment #2";
        status = RSVPStatus.DECLINED;
        response = responseManager.respond(event, player, status, comment);
        assertEquals(response.getStatus(), status);
        assertEquals(response.getComment(), comment);
    }
}