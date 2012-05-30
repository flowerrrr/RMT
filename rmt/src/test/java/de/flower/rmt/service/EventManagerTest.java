package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.*;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.Match;
import de.flower.rmt.model.db.entity.event.QEvent;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.hibernate.LazyInitializationException;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class EventManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testFindUpcomingAndLastNByUser() {
        // create team with some events in the future and some in the past
        Team team = testData.createTeamWithPlayers("test team", 5);
        int numFuture = 4;
        List<Event> events = testData.createEventsWithInvitations(team, numFuture, false);
        User user = team.getPlayers().get(0).getUser();
        // need to reload user to get fresh instance from database
        user = userRepo.findOne(user.getId());

        // now do the tests
        events = eventManager.findAllUpcomingAndLastNByUser(user, 5);
        assertEquals(events.size(), numFuture);

        // two events in the past
        testData.createEventsWithInvitations(team, 2, true);
        events = eventManager.findAllUpcomingAndLastNByUser(user, 5);
        assertEquals(events.size(), numFuture + 2);
    }

    @Test
    public void testFindLastNByUser() {
        // create team with some events in the future and some in the past
        Team team = testData.createTeamWithPlayers("test team", 4);
        List<Event> events = testData.createEventsWithInvitations(team, 2, true);
        User user = team.getPlayers().get(0).getUser();
        // need to reload user to get fresh instance from database
        user = userRepo.findOne(user.getId());

        events = eventManager.findAllUpcomingAndLastNByUser(user, 5);
        assertEquals(events.size(), 2);

        // create more past events
        testData.createEventsWithInvitations(team, 10, true);
        events = eventManager.findAllUpcomingAndLastNByUser(user, 5);
        assertEquals(events.size(), 5);
    }

    @Test
    public void testFindAllNextNHours() {
        Event event = testData.createEvent();
        List<Event> events;
        event.setDateTime(new DateTime().minusSeconds(1));
        eventManager.save(event);
        events = eventManager.findAllNextNHours(1);
        assertEquals(events.size(), 0);

        event.setDateTime(new DateTime().plusHours(1));
        eventManager.save(event);
        events = eventManager.findAllNextNHours(1);
        assertEquals(events.size(), 1);

        event.setDateTime(new DateTime().plusHours(1).plusMinutes(1));
        eventManager.save(event);
        events = eventManager.findAllNextNHours(1);
        assertEquals(events.size(), 0);
    }

    @Test
    public void testFindAllNextHoursRespectsCanceledFlag() {
        Event event = testData.createEvent();
        event.setDateTime(new DateTime().plusMinutes(1));
        eventManager.save(event);
        assertEquals(eventManager.findAllNextNHours(1).size(), 1);

        event.setCanceled(true);
        eventManager.save(event);
        assertEquals(eventManager.findAllNextNHours(1).size(), 0);
    }

    @Test
    public void testUpcomingEventsByUserEagerFetchesTeams() {
        Team team = testData.createTeamWithPlayers("test team", 10);
        List<Event> events = testData.createEventsWithInvitations(team, 3, true);
        List<Invitation> invitations = invitationManager.findAllByEvent(events.get(0), Invitation_.user);
        List<Event> list = eventManager.findAllUpcomingAndLastNByUser(invitations.get(0).getUser(), 3, QEvent.event.team);
        assertEquals(list.size(), 3);
        for (Event event : list) {
            // check if team can be accessed without LIE
            event.getTeam().getName();
        }
    }

    @Test
    public void testDelete() {
        Event event = testData.createEventWithResponses();
        eventManager.delete(event.getId());
        // assert that event is hard-deleted
        assertNull(eventRepo.findOne(event.getId()));
    }

    @Test
    public void testSurfaceUserType() {
        testData.setEventType(EventType.Match);
        // reload from database
        Match event = (Match) testData.createEvent();
        event = (Match) eventManager.loadById(event.getId());
        assertTrue(event.getSurfaceList().isEmpty());

        event.getSurfaceList().add(Surface.NATURAL_GRASS);
        event.getSurfaceList().add(Surface.ASH);
        eventManager.save(event);
        event = (Match) eventManager.loadById(event.getId());
        assertEquals(event.getSurfaceList().get(0), Surface.NATURAL_GRASS);
        assertEquals(event.getSurfaceList().get(1), Surface.ASH);
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
    public void testSorting() {
        Team team = testData.createTeamWithPlayers("test team", 10);
        User user = team.getPlayers().get(0).getUser();
        testData.createEventsWithInvitations(team, 10, true);
        testData.createEventsWithInvitations(team, 10, false);
        List<Event> events = eventManager.findAllUpcomingAndLastNByUser(user, 100);
        Date last = new DateTime().plusYears(1000).toDate();
        for (Event event : events) {
            assertTrue(event.getDateTimeAsDate().getTime() <= last.getTime());
            last = event.getDateTimeAsDate();
        }
    }
}