package de.flower.rmt.ui.manager.page.invitations;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class NotificationPage extends ManagerBasePage {

    public NotificationPage(final IModel<Event> model) {
        super(model);

        setHeading("manager.notification.heading");

        addMainPanel(new NotificationPanel(model));


    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.EVENTS;
    }
}
