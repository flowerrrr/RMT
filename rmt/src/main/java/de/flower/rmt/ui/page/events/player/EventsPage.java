package de.flower.rmt.ui.page.events.player;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.app.IEventListProvider;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.base.NavigationPanel;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
import de.flower.rmt.ui.panel.activityfeed.ActivityFeedPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventsPage extends PlayerBasePage {

    @SpringBean
    private IEventListProvider eventListProvider;

    @SpringBean
    private ISecurityService securityService;

    public EventsPage() {
        setHeading("player.events.heading");

        final UserModel userModel = new UserModel(securityService.getUser());
        addMainPanel(new EventListPanel(userModel, getEventListModel()));
        addSecondaryPanel(new ActivityFeedPanel());
    }

    private IModel<List<Event>> getEventListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventListProvider.getPlayerEventListPanelList();
            }
        };
    }

    @Override
     public String getActiveTopBarItem() {
         return NavigationPanel.EVENTS;
     }

}
