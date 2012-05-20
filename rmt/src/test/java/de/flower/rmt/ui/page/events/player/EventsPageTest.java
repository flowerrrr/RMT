package de.flower.rmt.ui.page.events.player;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class EventsPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        testData.createEvent();
        wicketTester.startPage(new EventsPage());
        wicketTester.dumpPage();
    }
}