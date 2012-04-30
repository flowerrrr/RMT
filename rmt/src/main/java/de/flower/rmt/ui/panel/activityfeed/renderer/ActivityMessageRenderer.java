package de.flower.rmt.ui.panel.activityfeed.renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class ActivityMessageRenderer {

    private final static Logger log = LoggerFactory.getLogger(ActivityMessageRenderer.class);

    private static List<IMessageRenderer<?>> renderers = new ArrayList<IMessageRenderer<?>>() {
        {
            add(new EventUpdateMessageRenderer());
            add(new EmailSentMessageRenderer());
            add(new InvitationUpdateMessageRenderer());
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
        log.warn("Unknown message type [" + message.getClass() + "].");
        return new DefaultMessageRenderer();
    }

    public static String toString(Object message) {
        return get(message).toString(message);
    }

    public static class DefaultMessageRenderer implements IMessageRenderer<Object> {

        @Override
        public String toString(Object message) {
            return message.toString();
        }

        @Override
        public boolean canHandle(final Object message) {
            return true;
        }
    }
}
