package de.flower.rmt.ui.manager.page.player;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractRMTWicketMokitoTests;
import de.flower.rmt.test.TestData;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ResetPasswordPanelTest extends AbstractRMTWicketMokitoTests {

    @Test
    public void testRender() {
        User user = TestData.newUser();
        wicketTester.startComponentInPage(new ResetPasswordPanel(Model.of(user)));
        wicketTester.dumpComponentWithPage();
        wicketTester.clickLink("resetButton");
        wicketTester.dumpComponentWithPage();
        wicketTester.assertVisible("feedbackul");
        wicketTester.assertContains(new ResourceModel("manager.player.resetpassword.success").getObject());
    }
}
