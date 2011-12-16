package de.flower.rmt.service;

import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * 
 * @author flowerrrr
 */

public class EventManagerTest extends AbstractIntegrationTests {

    @Test
    public void testFindUpcomingByUser() {
        // create team with some events in the future and some in the past
        Event event = testData.createEventWithResponses();
        DateTime now = new DateTime();
        User user = event.getTeam().getPlayers().get(0).getUser();
        // need to reload user to get fresh instance from database
        user = userRepo.findOne(user.getId());

        // now do the tests

        // one event in the past
        event.setDate(now.minusDays(1).toDate())  ;
        eventManager.save(event)    ;
        List<Event> events = eventManager.findAllUpcomingByUser(user)  ;
        assertEquals(events.size(), 0)  ;

        // one event in the future
        event.setDate(now.plusDays(1).toDate())  ;
        eventManager.save(event)      ;
        events = eventManager.findAllUpcomingByUser(user) ;
        assertEquals(events.size(), 1) ;

        // add another event scheduled for today
        event = testData.createEvent(event.getTeam(), true)  ;
        user = userRepo.findOne(user.getId()) ;
        events = eventManager.findAllUpcomingByUser(user);
        assertEquals(events.size(), 2)                 ;
    }

}