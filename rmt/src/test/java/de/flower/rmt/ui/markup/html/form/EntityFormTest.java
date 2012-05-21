package de.flower.rmt.ui.markup.html.form;

import de.flower.common.test.StringUtils;
import de.flower.common.test.wicket.WicketTesterHelper;
import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.test.Assert;
import de.flower.rmt.ui.markup.html.form.field.TextFieldPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.tester.FormTester;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;
import org.testng.annotations.Test;
import org.wicketstuff.jsr303.FormComponentBeanValidator;

import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

/**
 * @author flowerrrr
 */
public class EntityFormTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new EntityFormTestPage(new TestEntity()));
        wicketTester.dumpPage();
        wicketTester.debugComponentTrees();
        wicketTester.assertInvisible("hasErrors");
        wicketTester.assertComponent("submitButton", AjaxSubmitLink.class);
    }

    /**
     * Submit empty form.
     * Verify that feedback messages are not duplicated in global feebackpanel and field level feedback panels.
     */
    @Test
    public void testRenderError() {
        TestEntity entity = new TestEntity();
        wicketTester.startPage(new EntityFormTestPage(entity));
        wicketTester.dumpPage();
        wicketTester.clickLink("submitButton");
        wicketTester.dumpPage();
        wicketTester.assertVisible("hasErrors");
        wicketTester.assertErrorMessagesContains(TestEntity.notBlankAssertMessage);
        wicketTester.assertContains(TestEntity.notBlankAssertMessage);
        wicketTester.assertVisible("form:name:feedback:feedbackul");
        // does not work well here, cause it might give false positive
        wicketTester.assertInvisible("form:formFeedbackPanel:feedback:feedbackul");
        // must check by analyzing the page dump.
        int matches = StringUtils.countMatches(wicketTester.getLastResponseAsString(), TestEntity.notBlankAssertMessage);
        assertEquals(1, matches);
        wicketTester.debugComponentTrees();
    }

    /**
     * This test verifies that the implementation of debugComponentTrees
     * is overridden with a safe version that does not produce error logs and messes with
     * model objects.
     */
    @Test
    public void testDebugComponentTrees() {
        wicketTester.startPage(new EntityFormTestPage(new TestEntity2()));
        wicketTester.clickLink("submitButton");
        wicketTester.dumpPage();
        wicketTester.assertContains(TestEntity2.scriptAssertMessage);

        // now same thing again but this time with call of debugComponentTrees
        wicketTester.startPage(new EntityFormTestPage(new TestEntity2()));
        // in the original version this call will trigger evaluation of model objects that
        // then remain in invalid state. in this test case it would lead to not displaying
        // the error message.
        wicketTester.debugComponentTrees();
        wicketTester.clickLink("submitButton");
        wicketTester.dumpPage();
        wicketTester.assertContains(TestEntity2.scriptAssertMessage);
    }

    /**
     * A component with a property validator and an additional formbeanvalidator should
     * first use the propertyvalidator and stop validating if property validation fails.
     */
    @Test
    public void testValidationOrder() {
        wicketTester.startPage(new EntityFormTestPage(new TestEntity()));
        // must set value, empty fields are valid for @Size
        FormTester formTester = wicketTester.newFormTester("form");
        formTester.setValue("password:input", "1");
        wicketTester.clickLink("submitButton");
        // want to see only NotBlank and Size messages
        wicketTester.dumpPage();
        List<String> list = wicketTester.getMessagesAsString(FeedbackMessage.ERROR);
        log.info(list.toString());
        Assert.assertContains(list.get(0), TestEntity.notBlankAssertMessage);
        Assert.assertContains(list.get(1), TestEntity.sizeAssertMessage);
    }

    private static class EntityFormTestPage extends WebPage {

        public EntityFormTestPage(Object entity) {
            EntityForm<Object> form = new EntityForm<Object>("form", entity) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<Object> form) {

                }
            };
            add(form);
            form.add(new TextFieldPanel("name"));
            TextFieldPanel password = new TextFieldPanel("password");
            form.add(password);
            password.addValidator(new FormComponentBeanValidator(TestEntity.IScriptAssert.class));
        }

        @Override
        public Markup getAssociatedMarkup() {
            return WicketTesterHelper.creatPageMarkup("<html><body>\n"
                    + "<form wicket:id='form'>\n"
                    + "<div wicket:id='formFeedbackPanel' />"
                    // can use whatever tag we like, since only the body is rendered
                    + "<wicket:container wicket:id='name' labelKey='login.username' />\n"
                    + "<wicket:container wicket:id='password' labelKey='login.password' />\n"
                    + "<a wicket:id='submitButton' >save</a>"
                    + "</form>\n"
                    + "</body></html>", this);
        }
    }

    @ScriptAssert(script = "_this.name != 'foo';", lang = "javascript",
            message = TestEntity.scriptAssertMessage,
            groups = {TestEntity.IScriptAssert.class, Default.class}
    )
    private static class TestEntity implements Serializable {

        public interface IScriptAssert {

        }

        public final static String scriptAssertMessage = "__boundToComponent__";

        public final static String notBlankAssertMessage = "__notBlankMesssage__";

        public final static String sizeAssertMessage = "__sizeMesssage__";

        @NotBlank(message = TestEntity.notBlankAssertMessage)
        private String name;

        @Size(min = 6, max = 10, message = TestEntity.sizeAssertMessage)
        private String password;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(final String password) {
            this.password = password;
        }
    }

    @ScriptAssert(script = "false", lang = "javascript",
            message = TestEntity2.scriptAssertMessage)
    private static class TestEntity2 implements Serializable {

        public final static String scriptAssertMessage = "__unboundErrorMessage__";

        private String name;

        private String password;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(final String password) {
            this.password = password;
        }
    }
}
