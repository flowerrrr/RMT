package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.LineupItem;
import de.flower.rmt.model.db.entity.QLineupItem;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;

import java.util.List;


public class LineupManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testFindLineupItems() throws Exception {
        Event event = testData.createEventWithResponses();
        testData.createLineup(event);
        List<LineupItem> items = lineupManager.findLineupItems(event, QLineupItem.lineupItem.invitation);
        for (LineupItem item : items) {
            item.getInvitation().getName();
        }
    }
}
