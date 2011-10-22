package de.flower.rmt.ui.player.page.events;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class EventPage extends PlayerBasePage {

    public EventPage(final IModel<Event> model) {
        super(model);
    }
}
