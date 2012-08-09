package de.flower.rmt.ui.app;

import de.flower.rmt.service.ILinkProvider;
import de.flower.rmt.ui.page.blog.ArticlePage;
import de.flower.rmt.ui.page.event.player.EventPage;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.springframework.stereotype.Component;

/**
 * @author flowerrrr
 */
@Component
@Deprecated // should use LinkProvider instead
public class WicketLinkProvider implements ILinkProvider {

    @Override
    public String deepLinkEvent(final Long eventId) {
        String relativeUrl = urlForEvent(eventId).toString();
        return renderFullUrl(relativeUrl);
    }

    @Override
    public String deepLinkBlog(final Long articleId) {
        String relativeUrl = urlForArticle(articleId).toString();
        return renderFullUrl(relativeUrl);
    }

    private String renderFullUrl(String relativeUrl) {
        String url = RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(relativeUrl));
        return url;
    }

    private static CharSequence urlForEvent(Long eventId) {
        return RequestCycle.get().urlFor(EventPage.class, EventPage.getPageParams(eventId));
    }

    private static CharSequence urlForArticle(Long articleId) {
        return RequestCycle.get().urlFor(ArticlePage.class, ArticlePage.getPageParams(articleId));
    }
}
