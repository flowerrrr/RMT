package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.type.activity.EventUpdateMessage;
import de.flower.rmt.ui.app.Links;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author flowerrrr
 */
public class EventUpdateMessageRenderer implements IMessageRenderer<EventUpdateMessage> {

    @Override
    public String toString(final EventUpdateMessage message) {
        String key;
        Object[] params = new Object[]{Links.deepLinkEvent(message.getEventId())};
        if (message.isCreated()) {
            key = "activity.event.create.message";
        } else {
            key = "activity.event.update.message";
            params = new Object[]{EventMessageRenderer.getEventLink(message)};
        }
        String s = new StringResourceModel(key, Model.of(message), params).getObject();
        return s;
    }

    @Override
    public boolean canHandle(final Object message) {
        return message instanceof EventUpdateMessage;
    }
}
