package de.flower.rmt.service

import org.testng.annotations.Test
import de.flower.rmt.test.AbstractIntegrationTests
import org.joda.time.DateTime
import org.testng.Assert._

/**
 * 
 * @author flowerrrr
 */

class EventManagerTest extends AbstractIntegrationTests {

    @Test
    def testFindUpcomingByPlayer() {
        // create team with some events in the future and some in the past
        var event = testData.createEvent()
        val now = new DateTime()
        var user = event.getTeam().getPlayers().get(0).getUser()
        // need to reload user to get fresh instance from database
        user = userRepo.findOne(user.getId())

        // now do the tests

        // one event in the past
        event.setDate(now.minusDays(1).toDate())
        eventManager.save(event)
        var events = eventManager.findUpcomingByUserPlayer(user)
        assertEquals(events.size(), 0)

        // one event in the future
        event.setDate(now.plusDays(1).toDate())
        eventManager.save(event)
        events = eventManager.findUpcomingByUserPlayer(user)
        assertEquals(events.size(), 1)

        // add another event scheduled for today
        event = testData.createEventForTeam("Foo Fighters")
        // add user to this team
        teamManager.addPlayer(event.getTeam(), user)
        user = userRepo.findOne(user.getId())
        events = eventManager.findUpcomingByUserPlayer(user)
        assertEquals(events.size(), 2)
    }

}