package de.flower.rmt.ui.common.form;

import de.flower.rmt.test.AbstractWicketTests;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.Form;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class FormFeedbackPanelTest extends AbstractWicketTests {

    @Override
    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new FormFeedbackTestPanel());
        wicketTester.dumpComponentWithPage();
        wicketTester.assertInvisible("hasErrors");
        wicketTester.assertInvisible("submitSuccess");
    }

    @Test
    public void testRenderError() {
        String message = "jöksdifuwe9823hkldhf";
        wicketTester.getSession().getFeedbackMessages().error(null, message);
        wicketTester.startComponentInPage(new FormFeedbackTestPanel());
        wicketTester.dumpComponentWithPage();
        wicketTester.assertVisible("hasErrors");
        wicketTester.assertContains(message);
    }

    private static class FormFeedbackTestPanel extends BasePanel {

        public FormFeedbackTestPanel() {
            Form<?> form = new Form("form");
            form.add(new FormFeedbackPanel(null));
            add(form);
        }

        @Override
        public Markup getAssociatedMarkup() {
            return Markup.of("<wicket:panel><form wicket:id='form'><div wicket:id='formFeedbackPanel' /></form></wicket:panel>");
        }
    }
}
