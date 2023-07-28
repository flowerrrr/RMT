package de.flower.rmt.ui.page.events.manager;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;


public class InvitationSummaryPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        Event event = testData.newEvent();
        wicketTester.startComponentInPage(new InvitationSummaryPanel(Model.of(event)));
        wicketTester.dumpComponentWithPage();
    }

}
