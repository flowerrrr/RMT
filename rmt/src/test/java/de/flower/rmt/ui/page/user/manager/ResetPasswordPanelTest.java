package de.flower.rmt.ui.page.user.manager;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ResetPasswordPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        User user = testData.newUser();
        wicketTester.startComponentInPage(new ResetPasswordPanel(Model.of(user)));
        wicketTester.dumpComponentWithPage();
        wicketTester.clickLink("resetButton");
        wicketTester.dumpComponentWithPage();
        wicketTester.assertVisible("feedbackul");
        wicketTester.assertContains(new ResourceModel("manager.player.resetpassword.success").getObject());
    }
}
