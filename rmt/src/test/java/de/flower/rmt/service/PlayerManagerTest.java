package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 *
 * @author flowerrrr
 */

public class PlayerManagerTest extends AbstractIntegrationTests {

    @Test
    public void testFindByEventAndUser() {
        Event event = testData.createEventWithResponses();
        Player player = event.getTeam().getPlayers().get(0);
        User user = player.getUser();
        assertEquals(playerManager.findByEventAndUser(event, user), player);
    }

}