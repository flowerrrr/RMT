package de.flower.rmt.ui.player.page.events;

import de.flower.rmt.test.AbstractWicketTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class EventsPageTest extends AbstractWicketTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new EventsPage());
        wicketTester.dumpPage();
    }
}