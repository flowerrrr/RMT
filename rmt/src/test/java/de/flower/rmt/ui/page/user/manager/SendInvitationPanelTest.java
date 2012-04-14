package de.flower.rmt.ui.page.user.manager;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class SendInvitationPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        User user = testData.newUser();
        wicketTester.startComponentInPage(new SendInvitationPanel(Model.of(user)));
        wicketTester.dumpComponentWithPage();
        wicketTester.clickLink("sendButton");
        wicketTester.assertVisible("feedbackul");
    }
}
