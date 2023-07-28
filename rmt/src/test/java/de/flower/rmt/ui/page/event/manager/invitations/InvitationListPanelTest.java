package de.flower.rmt.ui.page.event.manager.invitations;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.EventModel;
import org.testng.annotations.Test;


public class InvitationListPanelTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        Event event = testData.createEventWithResponses();
        wicketTester.startComponentInPage(new InvitationListPanel(new EventModel(event)));
        wicketTester.dumpComponentWithPage();
    }
}
