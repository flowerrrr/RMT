package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Player;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 *
 * @author flowerrrr
 */

public class PlayerManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testFindByEventAndUser() {
        Event event = testData.createEventWithResponses();
        Player player = event.getTeam().getPlayers().get(0);
        User user = player.getUser();
        assertEquals(playerManager.findByEventAndUser(event, user), player);
    }

    @Test
    public void testFindAllByTeam() {
        Team team = testData.createTeamWithPlayers("FC Foo", 10);
        userManager.delete(team.getPlayers().get(1).getUser().getId());
        List<Player> players = playerManager.findAllByTeam(team);
        assertEquals(players.size(), 9);
    }

}