package de.flower.rmt.ui.app;

import de.flower.rmt.service.ILinkProvider;
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
        String url = RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(relativeUrl));
        return url;
    }

    private static CharSequence urlForEvent(Long eventId) {
        return RequestCycle.get().urlFor(EventPage.class, EventPage.getPageParams(eventId));
    }
}
