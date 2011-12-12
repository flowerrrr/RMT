package de.flower.common.ui.tooltips;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import de.flower.common.test.wicket.WicketTesterHelper;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class TwipsyBehaviorTest extends AbstractWicketUnitTests {

    private static String tooltip = "this is a tooltip";

    @Test
    public void testRender() {
        wicketTester.startPage(new TestPage());
        wicketTester.dumpPage();
        wicketTester.debugComponentTrees();
        wicketTester.assertContains(tooltip);
        wicketTester.assertContains("\\Q" + TwipsyBehavior.TWIPSY_JS + "\\E");
        wicketTester.assertContains(TwipsyBehavior.TWIPSY_JS_URL);
    }

    private static class TestPage extends WebPage {

        public TestPage() {
            Link link = new Link("link") {
                @Override
                public void onClick() {
                }
            };
            link.add(new TwipsyBehavior(Model.of(tooltip)));
            add(link);

            ExternalLink link2 = new ExternalLink("link2", "http://foo.bar");
            link2.add(new TwipsyBehavior(Model.<String>of("another tooltip")));
            add(link2);
        }

        @Override
        public Markup getAssociatedMarkup() {
            String html = "<html><head></head><body>\n"
                    + "<a wicket:id='link'>click me</a>\n"
                    + "<a wicket:id='link2'>click me</a>\n"
                    + "</body></html>";
            return WicketTesterHelper.creatPageMarkup(html, this);
        }
    }
}
