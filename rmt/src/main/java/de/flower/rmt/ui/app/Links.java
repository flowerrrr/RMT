package de.flower.rmt.ui.app;

import de.flower.rmt.ui.player.page.event.EventPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author flowerrrr
 */
public class Links {

    public static BookmarkablePageLink eventLink(String id, Long eventId) {
        return new BookmarkablePageLink(id, EventPage.class, new PageParameters().set(EventPage.PARAM_EVENTID, eventId));
    }


}
