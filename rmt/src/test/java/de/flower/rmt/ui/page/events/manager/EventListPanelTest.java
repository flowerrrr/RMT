package de.flower.rmt.ui.page.events.manager;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class EventListPanelTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new EventListPanel());
        wicketTester.dumpComponentWithPage();
    }

}
