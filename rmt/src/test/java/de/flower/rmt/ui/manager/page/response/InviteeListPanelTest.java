package de.flower.rmt.ui.manager.page.response;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.test.AbstractWicketIntegrationTests;
import de.flower.rmt.ui.model.EventModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class InviteeListPanelTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        Event event = testData.createEventWithResponses();
        wicketTester.startComponentInPage(new InviteeListPanel(new EventModel(event)));
        wicketTester.dumpComponentWithPage();
    }
}
