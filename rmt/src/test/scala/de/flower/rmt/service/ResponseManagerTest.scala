package de.flower.rmt.service

import de.flower.rmt.test.AbstractIntegrationTests
import de.flower.rmt.model.event.Event
import org.testng.Assert._
import org.testng.annotations.Test
import de.flower.rmt.model.{Response, Player, RSVPStatus}
import java.util.List
import scala.collection.JavaConversions._
import org.springframework.beans.factory.annotation.Autowired

/**
 *
 * @author flowerrrr
 */

class ResponseManagerTest extends AbstractIntegrationTests {

    @Test
    def testFindNotResponded() {
        var event: Event = testData.createEvent()
        var players: List[Player] = event.getTeam.getPlayers()
        assertTrue(!players.isEmpty)
        var notResponder = playerManager.findNotResponded(event)
        assertEquals(notResponder, players)

        responseManager.respond(event, players.get(0), RSVPStatus.ACCEPTED, "some comment")
        notResponder = playerManager.findNotResponded(event)
        assertEquals(players.size(), notResponder.size() + 1)
    }

    @Test
    def testFindResponder() {
        var event: Event = testData.createEvent()
        val players = event.getTeam().getPlayers()
        assertTrue(responseManager.findByEventAndStatus(event, RSVPStatus.ACCEPTED).isEmpty())
        var response = responseManager.respond(event, players.get(0), RSVPStatus.ACCEPTED, "some comment")
        assertEquals(response, responseManager.findByEventAndStatus(event, RSVPStatus.ACCEPTED).get(0))

        response = responseManager.respond(event, players.get(1), RSVPStatus.ACCEPTED, "some comment")
        assertTrue(responseManager.findByEventAndStatus(event, RSVPStatus.ACCEPTED).size() == 2)

    }

    @Test
    def testRespond() {
        val event = testData.createEvent()
        val player = event.getTeam().getPlayers().get(0)
        var comment = "Comment #1"
        var status = RSVPStatus.ACCEPTED
        // first initial response
        var response = responseManager.respond(event, player, status, comment)
        assertEquals(response.getStatus(), status)
        assertEquals(response.getComment(), comment)

        // update response for player
        comment = "Comment #2"
        status = RSVPStatus.DECLINED
        response = responseManager.respond(event, player, status, comment)
        assertEquals(response.getStatus(), status)
        assertEquals(response.getComment(), comment)

    }

}