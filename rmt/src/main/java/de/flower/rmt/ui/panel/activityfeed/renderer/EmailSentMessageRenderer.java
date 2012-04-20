package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.type.activity.EmailSentMessage;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author flowerrrr
 */
public class EmailSentMessageRenderer implements IMessageRenderer<EmailSentMessage> {

    @Override
    public String toString(final EmailSentMessage message) {
        Object[] params = new Object[]{EventMessageRenderer.getEventLink(message)};
        String s = new StringResourceModel("activity.event.notification.sent.message", Model.of(message), params).getObject();
        return s;
    }

}
