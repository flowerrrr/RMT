package de.flower.common.ui.feedback;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import de.flower.common.test.wicket.WicketTesterHelper;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class AlertMessageFeedbackPanelTest extends AbstractWicketUnitTests {

    private static boolean showInfo = true;

    private static boolean isVisible = true;

    @Test
    public void testRender() {
        wicketTester.startPage(TestPage.class);
        wicketTester.dumpPage();
        wicketTester.assertVisible("link");

        wicketTester.clickLink("link");
        wicketTester.dumpPage();
        wicketTester.assertVisible("link");
    }

    @Test
    public void testIsVisible() {
        wicketTester.startPage(TestPage.class);
        wicketTester.dumpPage();
        wicketTester.assertVisible("link");
        showInfo = false;
        wicketTester.clickLink("link");
        wicketTester.dumpPage();
        wicketTester.assertInvisible("feedbackul");
        showInfo = true;
        wicketTester.clickLink("nextPage");
        wicketTester.dumpPage();
        wicketTester.assertVisible("link");
    }

    @Test
    public void testMessageControlsVisibility() {
        wicketTester.startPage(TestPage.class);
        wicketTester.dumpPage();
        wicketTester.assertVisible("link");
        isVisible = false;
        wicketTester.clickLink("nextPage");
        wicketTester.dumpPage();
        wicketTester.assertInvisible("link");
        isVisible = true;
        wicketTester.clickLink("nextPage");
        wicketTester.dumpPage();
        wicketTester.assertVisible("link");
    }

    @Test
    public void testCloseButton() {
        wicketTester.startPage(TestPage.class);
        wicketTester.dumpPage();
        wicketTester.assertVisible("link");
        wicketTester.clickLink("closeButton");
        wicketTester.dumpAjaxResponse();
        // does not work for some reason, have to do string matching
        wicketTester.assertInvisible("link");
        wicketTester.assertContainsNot("class=\"alert-message");
        // but make sure that other messages are still visible
    }

    public static class TestPage extends WebPage {

        public TestPage() {

            add(new Link("nextPage") {

                @Override
                public void onClick() {
                    setResponsePage(new TestPage());
                }
            });

            add(new AlertMessageFeedbackPanel("panel"));

            AlertMessage message = new AlertMessage(Model.of("message"), Model.of("label")) {

                @Override
                protected boolean onClick(final AlertMessagePanel alertMessagePanel) {
                    setResponsePage(new TestPage());
                    return true;
                }

                @Override
                protected boolean isVisible() {
                    return isVisible;
                }
            };
            if (showInfo) {
                info(message);
            }
            info("hello world");
        }

        @Override
        public Markup getAssociatedMarkup() {
            String html = "<html><head></head><body>\n"
                    + "<div wicket:id='panel' />\n"
                    + "<a wicket:id='nextPage' />\n"
                    + "</body></html>";
            return WicketTesterHelper.creatPageMarkup(html, this);
        }
    }
}
