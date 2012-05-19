package de.flower.rmt.ui.page.error;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class InternalError500PageTest extends AbstractRMTWicketIntegrationTests {

    private RuntimeException exception = new RuntimeException("This is a test");

    @Test
    public void testRenderWithException() {
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
        });
        wicketTester.dumpPage();
        wicketTester.assertRenderedPage(InternalError500Page.class);
        wicketTester.assertContains("org.apache.wicket.markup.MarkupNotFoundException");

        // test another exception and make sure that errorpage does not display stale data (like it was in first version
        // of errorpage
        wicketTester.startPage(new WebPage() {
            @Override
            protected void onBeforeRender() {
                throw exception;
            }
        });
        wicketTester.dumpPage();
        wicketTester.assertRenderedPage(InternalError500Page.class);
        wicketTester.assertContains(exception.toString());
    }
}
