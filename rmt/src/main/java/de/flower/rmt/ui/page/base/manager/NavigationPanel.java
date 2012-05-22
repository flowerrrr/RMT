package de.flower.rmt.ui.page.base.manager;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.ui.app.IEventListProvider;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.markup.html.form.renderer.EventRenderer;
import de.flower.rmt.ui.page.base.AbstractNavigationPanel;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import de.flower.rmt.ui.page.event.manager.EventPage;
import de.flower.rmt.ui.page.event.manager.EventTabPanel;
import de.flower.rmt.ui.page.events.manager.EventsPage;
import de.flower.rmt.ui.page.opponents.manager.OpponentsPage;
import de.flower.rmt.ui.page.teams.manager.TeamsPage;
import de.flower.rmt.ui.page.users.manager.PlayersPage;
import de.flower.rmt.ui.page.venues.manager.VenuesPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class NavigationPanel extends AbstractNavigationPanel {

    public static final String HOME = "home";

    public static final String EVENTS = "events";

    public static final String TEAMS = "teams";

    public static final String PLAYERS = "players";

    public static final String OPPONENTS = "opponents";

    public static final String VENUES = "venues";

    @SpringBean
    private IEventListProvider eventListModelProvider;

    public NavigationPanel(INavigationPanelAware page) {
        super(View.MANAGER);

//        add(createMenuItem(HOME, ManagerHomePage.class, page));
        WebMarkupContainer events = createMenuItem(EVENTS, EventsPage.class, page);
        add(events);
        events.add(new ListView<Event>("eventList", getEventListModel()) {
            @Override
            protected void populateItem(final ListItem<Event> item) {
                Link link = new BookmarkablePageLink("link", EventPage.class,  EventPage.getPageParams(item.getModelObject().getId(), EventTabPanel.INVITATIONS_PANEL_INDEX));
                link.add(new Label("label", EventRenderer.getTypeDateSummary(item.getModelObject())));
                item.add(link);
            }
        });
        add(createMenuItem(TEAMS, TeamsPage.class, page));
        add(createMenuItem(PLAYERS, PlayersPage.class, page));
        add(createMenuItem(OPPONENTS, OpponentsPage.class, page));
        add(createMenuItem(VENUES, VenuesPage.class, page));
    }

    private IModel<List<Event>> getEventListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventListModelProvider.getManagerNavbarList();
            }
        };
    }
}
