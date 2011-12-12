package de.flower.rmt.ui.manager.page.player;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractWicketIntegrationTests;
import de.flower.rmt.ui.model.UserModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class SendInvitationPanelTest extends AbstractWicketIntegrationTests {

    @Override
    @Test
    public void testRender() {
        User user = testData.createUser();
        wicketTester.startComponentInPage(new SendInvitationPanel(new UserModel(user)));
        wicketTester.dumpComponentWithPage();
        wicketTester.clickLink("sendButton");
        wicketTester.assertVisible("feedbackul");
    }
}
