package de.flower.rmt.ui.manager;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.invitations.NotificationPanel;
import de.flower.rmt.ui.model.EventModel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class ManagerHomeMainPanel extends BasePanel {

    public ManagerHomeMainPanel() {
        super();

        final IModel<Event> model = new EventModel(4L);
        add(new NotificationPanel("panel", model));
    }
}
