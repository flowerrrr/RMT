package de.flower.rmt.ui.common.form;

import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.rmt.test.AbstractWicketTests;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.hibernate.validator.constraints.ScriptAssert;
import org.testng.annotations.Test;

import java.io.Serializable;

/**
 * @author flowerrrr
 */
public class EntityFormTest extends AbstractWicketTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new EntityFormTestPage());
        wicketTester.dumpPage();
        wicketTester.debugComponentTrees();
        wicketTester.assertInvisible("hasErrors");
        wicketTester.assertComponent("submitButton", MyAjaxSubmitLink.class);
    }

    @Test
    public void testRenderError() {
        wicketTester.startPage(new EntityFormTestPage());
        wicketTester.dumpPage();
        wicketTester.clickLink("submitButton");
        wicketTester.dumpPage();
        wicketTester.assertVisible("hasErrors");
        wicketTester.assertErrorMessages(TestEntity.message);
        wicketTester.assertContains(TestEntity.message);
    }

    /**
     * This test verifies that the implementation of debugComponentTrees
     * is overridden with a safe version that does not produce error logs and messes with
     * model objects.
     */
    @Test
    public void testDebugComponentTrees() {
        wicketTester.startPage(new EntityFormTestPage());
        wicketTester.clickLink("submitButton");
        wicketTester.dumpPage();
        wicketTester.assertContains(TestEntity.message);

        // now same thing again but this time with call of debugComponentTrees
        wicketTester.startPage(new EntityFormTestPage());
        // in the original version this call will trigger evaluation of model objects that
        // then remain in invalid state. in this test case it would lead to not displaying
        // the error message.
        wicketTester.debugComponentTrees();
        wicketTester.clickLink("submitButton");
        wicketTester.dumpPage();
        wicketTester.assertContains(TestEntity.message);
    }

    private static class EntityFormTestPage extends WebPage {

        public EntityFormTestPage() {
            TestEntity entity = new TestEntity();
            EntityForm<TestEntity> form = new EntityForm<TestEntity>("form", entity) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<TestEntity> form) {

                }
            };
            add(form);
            form.add(new TextFieldPanel("name"));
        }

        @Override
        public Markup getAssociatedMarkup() {
            return Markup.of("<html><body>\n"
                    + "<form wicket:id='form'>\n"
                    + "<div wicket:id='formFeedbackPanel' />"
                    // can use whatever tag we like, since only the body is rendered
                    + "<wicket:container wicket:id='name' labelKey='label.name' />\n"
                    + "<a wicket:id='submitButton' >save</a>"
                    + "</form>\n"
                    + "</body></html>");
        }
    }

    @ScriptAssert(script = "false;", lang = "javascript", message = TestEntity.message)
    private static class TestEntity implements Serializable {

        public final static String message = "__messsage__";

        private String name;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }
}
