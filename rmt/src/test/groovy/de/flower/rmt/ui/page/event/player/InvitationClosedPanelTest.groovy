package de.flower.rmt.ui.page.event.player

import de.flower.rmt.test.AbstractRMTWicketMockitoTests
import de.flower.rmt.ui.page.event.player.EventSecondaryPanel.InvitationClosedPanel
import org.apache.wicket.model.Model
import org.testng.annotations.Test

/**
 * @author flowerrrr
 */
public class InvitationClosedPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        def event = testData.newEvent();
        wicketTester.startComponentInPage(new InvitationClosedPanel(Model.of(event)))
        wicketTester.dumpComponentWithPage()
    }

}
