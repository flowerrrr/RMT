package de.flower.rmt.ui.page.events.manager;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;


public class EventListPanelTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new EventListPanel());
        wicketTester.dumpComponentWithPage();
    }

}
