package de.flower.rmt.service

import de.flower.rmt.test.AbstractIntegrationTests
import org.testng.Assert._
import org.testng.annotations.Test
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