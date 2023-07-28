package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.db.type.activity.EmailSentMessage;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;


public class EmailSentMessageRenderer extends AbstractEventMessageRenderer implements IMessageRenderer<EmailSentMessage> {

    @Override
    public String toString(final EmailSentMessage message) {
        Object[] params = new Object[]{ getEventArticle(message), getEventLink(message) };
        String s = new StringResourceModel("activity.event.notification.sent.message", Model.of(message), params).getObject();
        return s;
    }

    @Override
    public boolean canHandle(final Object message) {
        return message instanceof EmailSentMessage;
    }
}
