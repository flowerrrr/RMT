package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Lineup;
import de.flower.rmt.model.db.entity.LineupItem;
import de.flower.rmt.model.db.entity.QLineupItem;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author flowerrrr
 */
public class LineupManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testFindLineupItems() throws Exception {
        Event event = testData.createEventWithResponses();
        Lineup lineup = testData.createLineup(event);
        List<LineupItem> items = lineupManager.findLineupItems(event, QLineupItem.lineupItem.invitation);
        for (LineupItem item : items) {
            item.getInvitation().getName();
        }
    }
}
