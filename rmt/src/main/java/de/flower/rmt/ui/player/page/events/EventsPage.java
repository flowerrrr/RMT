package de.flower.rmt.ui.player.page.events;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.panel.ActivityFeedPanel;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.player.NavigationPanel;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventsPage extends PlayerBasePage {

    @SpringBean
    private IEventManager eventManager;

    public EventsPage() {
        setHeading("player.events.heading");

        final UserModel userModel = new UserModel(getUserDetails().getUser());
        addMainPanel(new EventListPanel(userModel, getUpcomingEventList(userModel)));
        addSecondaryPanel(new ActivityFeedPanel());
    }

    private IModel<List<Event>> getUpcomingEventList(final UserModel userModel) {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAllUpcomingByUser(userModel.getObject());
            }
        };
    }

    @Override
     public String getActiveTopBarItem() {
         return NavigationPanel.EVENTS;
     }

}
