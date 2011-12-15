package de.flower.rmt.ui.manager.page.events;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import de.flower.rmt.test.TestData;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class InvitationSummaryPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        Event event = TestData.newEvent();
        wicketTester.startComponentInPage(new InvitationSummaryPanel(Model.of(event)));
        wicketTester.dumpComponentWithPage();
    }

}
