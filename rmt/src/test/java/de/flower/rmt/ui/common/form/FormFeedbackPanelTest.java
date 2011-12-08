package de.flower.rmt.ui.common.form;

import de.flower.rmt.test.AbstractWicketTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class FormFeedbackPanelTest extends AbstractWicketTests {

    @Override
    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new FormFeedbackPanel(null));
        wicketTester.dumpComponentWithPage();
        wicketTester.assertInvisible("hasErrors");
    }

    @Test
    public void testRenderError() {
        String message = "jöksdifuwe9823hkldhf";
        wicketTester.getSession().getFeedbackMessages().error(null, message);
        wicketTester.startComponentInPage(new FormFeedbackPanel(null));
        wicketTester.dumpComponentWithPage();
        wicketTester.assertVisible("hasErrors");
        wicketTester.assertContains(message);
    }
}
