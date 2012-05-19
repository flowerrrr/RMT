package de.flower.rmt.ui.page.event.player;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.EventModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class EventPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        Event event = testData.createEventWithResponses();
        wicketTester.startPage(new EventPage(new EventModel(event)));
        wicketTester.dumpPage();
        wicketTester.dumpComponentWithPage();
    }
}