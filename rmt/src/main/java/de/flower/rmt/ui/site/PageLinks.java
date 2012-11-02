package de.flower.rmt.ui.site;

import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.page.event.player.EventPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * @author flowerrrr
 */
public class PageLinks {

    public static BookmarkablePageLink eventLink(String id, Long eventId) {
        return eventLink(id, eventId, View.PLAYER);
    }

    public static BookmarkablePageLink eventLink(String id, Long eventId, View view) {
        if (view == View.MANAGER) {
            return new BookmarkablePageLink(id, de.flower.rmt.ui.page.event.manager.EventPage.class, EventPage.getPageParams(eventId));
        } else {
            return new BookmarkablePageLink(id, EventPage.class, EventPage.getPageParams(eventId));
        }
    }

}
