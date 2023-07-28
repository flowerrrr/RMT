package de.flower.rmt.ui.site;

import de.flower.rmt.service.ui.UrlProvider;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.page.event.player.EventPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class WicketUrlProviderTest extends AbstractRMTWicketIntegrationTests {

    @Autowired
    private WicketUrlProvider wicketUrlProvider;

    @Autowired
    private UrlProvider urlProvider;

    /**
     * Verify that bookmarkable urls are generated the way we expect it.
     */
    @Test
    public void testDeepLink() {
        Long eventId = 1L;
        CharSequence url = wicketUrlProvider.deepLinkEvent(eventId);
        assertEquals(url, "http://localhost/context/servlet/event/1");
        BookmarkablePageLink link =  new BookmarkablePageLink("id", EventPage.class, EventPage.getPageParams(eventId));
        url = (CharSequence) ReflectionTestUtils.invokeGetterMethod(link, "getURL");
        assertEquals(url.toString(), "./event/1"); // ./ prefix required after upgrading to wicket 1.5.7

        url = urlProvider.deepLinkEvent(eventId);
        assertEquals(url, "http://localhost/context/servlet/event/1");

        Long articleId = 1L;
        url = wicketUrlProvider.deepLinkBlog(articleId);
        assertEquals(url, "http://localhost/context/servlet/blog/1");
    }
}
