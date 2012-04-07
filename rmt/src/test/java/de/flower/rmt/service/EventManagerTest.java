package de.flower.rmt.service;

import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.Invitation_;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.*;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.hibernate.LazyInitializationException;
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

    @Test
    public void testUpcomingEventsByUser() {
        Event event = testData.createEvent();
        List<Invitation> invitations = invitationManager.findAllByEvent(event, Invitation_.user);
        List<Event> list = eventManager.findAllUpcomingByUser(invitations.get(0).getUser());
        // check if team can be accessed without LIE
        list.get(0).getTeam().getName();
    }

    @Test
    public void testDelete() {
        Event event = testData.createEventWithResponses();
        eventManager.delete(event.getId());
        // assert that event is hard-deleted
        assertNull(eventRepo.findOne(event.getId()));
    }

    @Test
    public void testLoadPrefetchedAssociation() {
        testData.setEventType(EventType.Match);
        Match event = (Match) testData.createEvent();
        // reload from database
        event = (Match) eventManager.loadById(event.getId(), Event_.team, Event_.venue, Match_.opponent, AbstractSoccerEvent_.surfaceList);
        event.getTeam().getName();
        event.getVenue().getName();
        event.getOpponent().getName();
        event.getSurfaceList().isEmpty();
    }

    @Test(expectedExceptions = LazyInitializationException.class)
    public void testLoadNoPrefetchedAssociations() {
        testData.setEventType(EventType.Match);
        Event event = testData.createEvent();
        // reload from database
        event = eventManager.loadById(event.getId());
        event.getTeam().getName();
    }

    @Test
    public void debugMetaModel() {
        log.info(AbstractSoccerEvent_.surfaceList.toString());
        log.info(Match_.surfaceList.toString());
        assertEquals(AbstractSoccerEvent_.surfaceList, Match_.surfaceList);
    }

}