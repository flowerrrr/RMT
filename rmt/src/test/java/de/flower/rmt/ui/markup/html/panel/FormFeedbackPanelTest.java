package de.flower.rmt.ui.markup.html.panel;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import de.flower.common.ui.panel.BasePanel;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.Form;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class FormFeedbackPanelTest extends AbstractWicketUnitTests {

    protected final static String message = "jöksdifuwe9823hkldhf";

    protected static boolean renderError = false;

    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new FormFeedbackTestPanel());
        wicketTester.dumpComponentWithPage();
        wicketTester.assertInvisible("hasErrors");
        wicketTester.assertInvisible("submitSuccess");
    }

    @Test
    public void testRenderError() {
        FormFeedbackTestPanel panel = new FormFeedbackTestPanel();
        renderError = true;
        wicketTester.startComponentInPage(panel);
        wicketTester.dumpComponentWithPage();
        wicketTester.assertVisible("hasErrors");
        wicketTester.assertContains(message);
    }

    private static class FormFeedbackTestPanel extends BasePanel {

        Form form;

        public FormFeedbackTestPanel() {
            form = new Form("form");
            form.add(new FormFeedbackPanel(form));
            add(form);
        }

        @Override
        protected void onBeforeRender() {
            super.onBeforeRender();
            if (renderError) {
                getSession().getFeedbackMessages().error(form, message);
            }
        }

        @Override
        public Markup getAssociatedMarkup() {
            return Markup.of("<wicket:panel><form wicket:id='form'><div wicket:id='formFeedbackPanel' /></form></wicket:panel>");
        }
    }
}
