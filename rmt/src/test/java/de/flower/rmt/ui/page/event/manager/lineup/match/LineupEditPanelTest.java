package de.flower.rmt.ui.page.event.manager.lineup.match;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.EventModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class LineupEditPanelTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        Event event = testData.createEventWithResponses();
        testData.createLineup(event);
        wicketTester.startComponentInPage(new LineupEditPanel("panel", new EventModel(event)));
        wicketTester.dumpComponentWithPage();
    }
}
