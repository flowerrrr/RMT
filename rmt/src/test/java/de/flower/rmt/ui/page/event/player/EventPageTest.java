package de.flower.rmt.ui.page.event.player;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class EventPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        Event event = testData.createEventWithResponses();
        wicketTester.startPage(EventPage.class, EventPage.getPageParams(event.getId()));
        wicketTester.dumpPage();
        wicketTester.dumpComponentWithPage();
    }
}