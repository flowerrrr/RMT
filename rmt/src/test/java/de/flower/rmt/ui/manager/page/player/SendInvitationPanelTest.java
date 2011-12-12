package de.flower.rmt.ui.manager.page.player;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractRMTWicketMokitoTests;
import de.flower.rmt.test.TestData;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class SendInvitationPanelTest extends AbstractRMTWicketMokitoTests {

    @Override
    @Test
    public void testRender() {
        User user = TestData.newUser();
        wicketTester.startComponentInPage(new SendInvitationPanel(Model.of(user)));
        wicketTester.dumpComponentWithPage();
        wicketTester.clickLink("sendButton");
        wicketTester.assertVisible("feedbackul");
    }
}
