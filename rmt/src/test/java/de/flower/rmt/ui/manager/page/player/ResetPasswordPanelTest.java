package de.flower.rmt.ui.manager.page.player;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractWicketIntegrationTests;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.model.ResourceModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ResetPasswordPanelTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        User user = testData.createUser();
        wicketTester.startComponentInPage(new ResetPasswordPanel(new UserModel(user)));
        wicketTester.dumpComponentWithPage();
        wicketTester.clickLink("resetButton");
        wicketTester.dumpComponentWithPage();
        wicketTester.assertVisible("feedbackul");
        wicketTester.assertContains(new ResourceModel("manager.player.resetpassword.success").getObject());
    }
}
