package de.flower.rmt.ui.page.account;

import de.flower.rmt.model.User;
import de.flower.rmt.service.type.Password;
import de.flower.rmt.test.AbstractWicketIntegrationTests;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.tester.FormTester;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class AccountPasswordPanelTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        User user = testData.getTestUser();
        wicketTester.startComponentInPage(new AccountPasswordPanel("panel", new UserModel(user)));
        wicketTester.dumpComponentWithPage();
    }

    @Test
    public void testValidation() {
        User user = testData.createUser();
        wicketTester.startComponentInPage(new AccountPasswordPanel("panel", new UserModel(user)));
        wicketTester.dumpComponentWithPage();
        FormTester formTester = wicketTester.newFormTester("form");
        formTester.setValue("oldPassword:input", "uifaöjsdkflöajdsf");
        formTester.setValue("newPassword:input", "jkalsjdkfldsjf");
        formTester.setValue("newPasswordRepeat:input", "jkalsjdkfldsjf");
        wicketTester.clickLink("submitButton");
        wicketTester.dumpComponentWithPage();
        String key = Password.Validation.passwordNotValidMessage.replace("{", "").replace("}", "");
        String message = new ResourceModel(key).getObject();
        wicketTester.assertErrorMessagesContains(new String[] {message});
        wicketTester.assertInvisible("form:formFeedbackPanel:feedback:feedbackul:messages");
        wicketTester.assertVisible("form:oldPassword:feedback:feedbackul:messages");
        wicketTester.assertContains(message);
    }
}
