package de.flower.rmt.ui.panel.activityfeed.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class ActivityMessageRenderer {

    private final static List<IMessageRenderer<?>> renderers = new ArrayList<IMessageRenderer<?>>() {
        {
            add(new EventUpdateMessageRenderer());
            add(new EmailSentMessageRenderer());
            add(new InvitationUpdateMessageRenderer());
            add(new InvitationUpdateMessageRenderer2());
            add(new BlogUpdateMessageRenderer());
        }
    };

    private ActivityMessageRenderer() {
    }

    private static IMessageRenderer get(final Object message) {
        for (IMessageRenderer<?> renderer : renderers) {
            if (renderer.canHandle(message)) {
                return renderer;
            }
        }
        throw new IllegalArgumentException("Unknown message type [" + message.getClass() + "].");
    }

    public static String toString(Object message) {
        return get(message).toString(message);
    }

}
