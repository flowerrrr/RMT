package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.type.activity.EmailSentMessage;
import de.flower.rmt.model.type.activity.EventUpdateMessage;
import de.flower.rmt.model.type.activity.InvitationUpdateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class ActivityMessageRenderer {

    private final static Logger log = LoggerFactory.getLogger(ActivityMessageRenderer.class);

    private ActivityMessageRenderer() {}

    private static IMessageRenderer get(final Object message) {
        if (message instanceof EventUpdateMessage) {
            return new EventUpdateMessageRenderer();
        } else if (message instanceof EmailSentMessage) {
            return new EmailSentMessageRenderer();
        } else if (message instanceof InvitationUpdateMessage) {
            return new InvitationUpdateMessageRenderer();
        } else {
            log.warn("Unknown message type [" + message.getClass() + "].");
            return new IMessageRenderer<Object>() {
                @Override
                public String toString(Object message) {
                    return message.toString();
                }
            };
        }
    }

    public static String toString(Object message) {
        return get(message).toString(message);
    }
}
