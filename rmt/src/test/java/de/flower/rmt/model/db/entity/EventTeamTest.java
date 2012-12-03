package de.flower.rmt.model.db.entity;

import de.flower.rmt.test.TestData;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class EventTeamTest {

    @Test
    public void testAddPlayerReorders() {
        EventTeam eventTeam = new EventTeam(new TestData().newEvent());
        EventTeamPlayer p1 = new EventTeamPlayer(eventTeam, null);
        eventTeam.addPlayer(p1, null);
        assertOrder(p1, 0);
        EventTeamPlayer p2 = new EventTeamPlayer(eventTeam, null);
        eventTeam.addPlayer(p2, null);
        assertOrder(p2, 1);
        EventTeamPlayer p3 = new EventTeamPlayer(eventTeam, null);
        eventTeam.addPlayer(p3, p1);
        assertOrder(p3, 0);
        assertOrder(p1, 1);
        assertOrder(p2, 2);
        assertOrdered(eventTeam.getPlayers());

        // add player before itself
        eventTeam.addPlayer(p3, p3);
    }

    private void assertOrdered(final List<EventTeamPlayer> players) {
        int order = 0;
        for (EventTeamPlayer player : players) {
            assertOrder(player, order);
            order++;
        }

    }

    private void assertOrder(final EventTeamPlayer player, Integer expectedOrder) {
        assertEquals(player.getOrder(), expectedOrder);
    }
}
