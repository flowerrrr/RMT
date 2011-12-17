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

    private static boolean showLink1 = true;

    private static boolean isVisible = true;

    String link1 = "messages:0:message:link";

    String link2 = "messages:1:message:link";

    @Test
    public void testRender() {
        wicketTester.startPage(TestPage.class);
        wicketTester.dumpPage();
        wicketTester.assertVisible(link1);
        wicketTester.assertVisible(link2);

        wicketTester.clickLink(link1);
        wicketTester.dumpPage();
        wicketTester.assertVisible(link1);
    }

    @Test
    public void testIsVisible() {
        wicketTester.startPage(TestPage.class);
        wicketTester.dumpPage();
        wicketTester.assertVisible(link1);
        showLink1 = false;
        wicketTester.clickLink(link1);
        wicketTester.dumpPage();
        wicketTester.assertContainsNot("messag 1");
        showLink1 = true;
        wicketTester.clickLink("nextPage");
        wicketTester.dumpPage();
        wicketTester.assertVisible(link1);
    }

    @Test
    public void testMessageControlsVisibility() {
        wicketTester.startPage(TestPage.class);
        wicketTester.dumpPage();
        wicketTester.assertVisible(link1);
        isVisible = false;
        wicketTester.clickLink("nextPage");
        wicketTester.dumpPage();
        wicketTester.assertInvisible(link1);
        isVisible = true;
        wicketTester.clickLink("nextPage");
        wicketTester.dumpPage();
        wicketTester.assertVisible(link1);
    }

    @Test
    public void testCloseButton() {
        wicketTester.startPage(TestPage.class);
        wicketTester.dumpPage();
        wicketTester.assertVisible(link1);
        wicketTester.assertVisible(link2);
        wicketTester.clickLink("messages:0:message:closeButton");
        wicketTester.dumpAjaxResponse();
        // does not work for some reason, have to do string matching
        wicketTester.assertInvisible(link1);
        // wicketTester.assertVisible(link2); does not work, must use string compare
        // assert that the component is updated through ajax
        wicketTester.assertContains("\\Q<ajax-response><component id=\"message2\" ><![CDATA[]]></component></ajax-response>\\E");
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

            if (showLink1) {
                AlertMessage message = new AlertMessage(Model.of("message 1"), Model.of("label 1")) {

                    @Override
                    protected boolean onClick(final AlertMessagePanel alertMessagePanel) {
                        setResponsePage(new TestPage());
                        return false;
                    }

                    @Override
                    protected boolean isVisible() {
                        return isVisible;
                    }
                };
                info(message);
            }
            // add another message that is always visible
            AlertMessage message = new AlertMessage(Model.of("message 2"), Model.of("label 2")) {

                @Override
                protected boolean onClick(final AlertMessagePanel alertMessagePanel) {
                    setResponsePage(new TestPage());
                    return false;
                }

                @Override
                protected boolean isVisible() {
                    return isVisible;
                }
            };
            info(message);
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
