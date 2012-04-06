package de.flower.rmt.ui.app;

import de.flower.rmt.ui.player.page.event.EventPage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author flowerrrr
 */
public class Links {

    public static BookmarkablePageLink eventLink(String id, Long eventId) {
        return new BookmarkablePageLink(id, EventPage.class, new PageParameters().set(EventPage.PARAM_EVENTID, eventId));
    }

    private static CharSequence urlForEvent(Long eventId) {
        return RequestCycle.get().urlFor(de.flower.rmt.ui.player.page.event.EventPage.class, new PageParameters().set(de.flower.rmt.ui.player.page.event.EventPage.PARAM_EVENTID, eventId));
    }

    public static String deepLinkEvent(Long eventId) {
        // relative url might contain ../../player/event/2
        String relativeUrl = urlForEvent(eventId).toString();
        return toAbsoluteUrl(relativeUrl);
    }

    public static String toAbsoluteUrl(String relativeUrl) {
        String requestUrl = RequestCycle.get().getRequest().getUrl().toString();
        String url = RequestUtils.toAbsolutePath(requestUrl, relativeUrl);
        return RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(url));
    }

    public static class HistoryBackLink extends ExternalLink {

        public HistoryBackLink(final String id) {
            super(id, "#");
            add(AttributeModifier.replace("onclick", "window.history.back();return false;"));
        }
    }
}
