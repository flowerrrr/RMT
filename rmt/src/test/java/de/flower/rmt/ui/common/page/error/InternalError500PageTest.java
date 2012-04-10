package de.flower.rmt.ui.common.page.error;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class InternalError500PageTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRenderWithException() {
        RuntimeException exception = new RuntimeException("This is a test");
        wicketTester.getSession().error(exception);
        wicketTester.startPage(InternalError500Page.class);
        wicketTester.dumpPage();
        wicketTester.assertContains(exception.toString());
    }

    @Test
    public void testRenderWithoutException() {
        wicketTester.startPage(InternalError500Page.class);
        wicketTester.dumpPage();
    }

    @Test
    public void testErrorPageRenderedWhenException() {
        wicketTester.setExposeExceptions(false); // wickettester will use standard error page like app would do.
        wicketTester.startPage(new WebPage() {
            {
                add(new Label("unknownComponent", "foobar"));
            }
        }) ;
        wicketTester.dumpPage();
        wicketTester.assertRenderedPage(InternalError500Page.class);
        wicketTester.assertContains("org.apache.wicket.markup.MarkupNotFoundException");
    }

}
