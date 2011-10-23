package de.flower.rmt.service

import de.flower.rmt.test.AbstractIntegrationTests
import de.flower.rmt.model.event.Event
import org.testng.Assert._
import org.testng.annotations.Test
import de.flower.rmt.model.{Player, RSVPStatus}
import java.util.List
/**
 *
 * @author flowerrrr
 */

class PlayerManagerTest extends AbstractIntegrationTests {

    @Test
    def testFindByEventAndUser() {
        val event = testData.createEvent()
        val player = event.getTeam().getPlayers().get(0)
        val user = player.getUser()
        assertEquals(playerManager.findByEventAndUser(event, user), player)
    }

}