package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.type.activity.AbstractEventMessage;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.util.Dates;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author flowerrrr
 */
public class EventMessageRenderer {

    public static String getEventLink(AbstractEventMessage message) {
        Object[] params = new Object[]{Links.deepLinkEvent(message.getEventId()), Dates.formatDateShort(message.getEventDate())};
        String eventLink = new StringResourceModel("activity.${eventType}.tmpl", Model.of(message), params).getObject();
        return eventLink;
    }

    public static String getEventArticle(AbstractEventMessage message) {
        return new StringResourceModel("activity.${eventType}.2.tmpl", Model.of(message)).getObject();
    }
}
