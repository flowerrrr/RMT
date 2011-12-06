package de.flower.rmt.ui.player.page.event;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.test.WicketTests;
import de.flower.rmt.ui.model.EventModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class EventPageTest extends WicketTests {

    @Test
    public void renderPage() {
        Event event = testData.createEventWithResponses();
        wicketTester.startPage(new EventPage(new EventModel(event)));
        wicketTester.dumpPage();
    }
}