package de.flower.rmt.ui.page.base;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.ui.app.IEventListProvider;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.markup.html.form.renderer.EventRenderer;
import de.flower.rmt.ui.page.account.AccountPage;
import de.flower.rmt.ui.page.base.manager.ManagerHomePage;
import de.flower.rmt.ui.page.calendar.CalendarPage;
import de.flower.rmt.ui.page.event.manager.EventPage;
import de.flower.rmt.ui.page.event.manager.EventTabPanel;
import de.flower.rmt.ui.page.events.manager.EventsPage;
import de.flower.rmt.ui.page.opponents.manager.OpponentsPage;
import de.flower.rmt.ui.page.teams.manager.TeamsPage;
import de.flower.rmt.ui.page.users.UsersPage;
import de.flower.rmt.ui.page.venues.manager.VenuesPage;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class NavigationPanel extends RMTBasePanel {

    public static final String EVENTS = "events";

    public static final String CALENDAR = "calendar";

    public static final String TEAMS = "teams";

    public static final String USERS = "users";

    public static final String OPPONENTS = "opponents";

    public static final String VENUES = "venues";

    @SpringBean
    private IEventListProvider eventListModelProvider;

    public NavigationPanel(INavigationPanelAware page) {

        setRenderBodyOnly(true);

        add(Links.aboutLink("about"));

        WebMarkupContainer events = new WebMarkupContainer("events");
        if (getView() == View.MANAGER) {
            addMenuItem(events, EVENTS, EventsPage.class, page, true);
        } else {
            addMenuItem(events, EVENTS, de.flower.rmt.ui.page.events.player.EventsPage.class, page, true);
        }
        add(events);
        events.add(new AjaxEventListener(Event.class));
        events.add(new ListView<Event>("eventList", getEventListModel()) {
            @Override
            protected void populateItem(final ListItem<Event> item) {
                Link link;
                if (getView() == View.MANAGER) {
                    link = new BookmarkablePageLink("link", EventPage.class, EventPage.getPageParams(item.getModelObject().getId(), EventTabPanel.INVITATIONS_PANEL_INDEX));
                } else {
                    link = new BookmarkablePageLink("link", de.flower.rmt.ui.page.event.player.EventPage.class, EventPage.getPageParams(item.getModelObject().getId()));
                }
                link.add(new Label("label", EventRenderer.getTypeDateSummary(item.getModelObject())));
                item.add(link);
            }
        });

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(CALENDAR, CalendarPage.class, page, true));
        menuItems.add(new MenuItem(TEAMS, TeamsPage.class, page, getView() == View.MANAGER));
        menuItems.add(new MenuItem(USERS, UsersPage.class, page, true));
        menuItems.add(new MenuItem(OPPONENTS, OpponentsPage.class, page, getView() == View.MANAGER));
        // TODO (flowerrrr - 24.06.12) unify both pages
        menuItems.add(new MenuItem(VENUES, VenuesPage.class, page, getView() == View.MANAGER));
        menuItems.add(new MenuItem(VENUES, de.flower.rmt.ui.page.venues.player.VenuesPage.class, page, getView() != View.MANAGER));

        ListView<MenuItem> menuList = new ListView<MenuItem>("menuList", menuItems) {
            @Override
            protected void populateItem(final ListItem<MenuItem> item) {
                MenuItem menuItem = item.getModelObject();
                addMenuItem(item, menuItem.pageName, menuItem.targetPageClass, menuItem.currentPage, menuItem.visible);
            }
        };
        add(menuList);

        add(new BookmarkablePageLink("account", AccountPage.class));
        add(createSwitchViewLink("switchView", getView()));
        add(Links.logoutLink("logoutLink"));
        add(new Label("user", getUser().getFullname()));
    }

    private IModel<List<Event>> getEventListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                if (getView() == View.MANAGER) {
                    return eventListModelProvider.getManagerNavbarList();
                } else {
                    return eventListModelProvider.getPlayerNavbarList();
                }
            }
        };
    }

    /**
     * Depending on user role (manager or player) a link is generated that
     * allows the user to switch beetween both views.
     *
     * @return
     */
    private Link createSwitchViewLink(String id, View view) {
        Link link;
        if (view == View.MANAGER) {
            // manager can always switch to player view.
            link = new BookmarkablePageLink(id, de.flower.rmt.ui.page.events.player.EventsPage.class);
            link.add(new Label("label", new ResourceModel("navigation.switch.player").getObject()));
        } else {
            // player can only switch to manager mode if he has MANAGER role
            link = new BookmarkablePageLink(id, ManagerHomePage.class);
            link.setVisible(getUser().isManager());
            link.add(new Label("label", new ResourceModel("navigation.switch.manager").getObject()));
        }
        return link;
    }

    public void addMenuItem(final WebMarkupContainer item, String pageName, Class<?> pageClass, final INavigationPanelAware page, boolean visible) {
        BookmarkablePageLink link = new BookmarkablePageLink("link", pageClass);
        link.add(new Label("label", new ResourceModel("navigation." + pageName.toLowerCase())));
        item.add(link);
        if (page != null && page.getActiveTopBarItem().equals(pageName)) {
            item.add(AttributeModifier.append("class", "active"));
        }
        item.setVisible(visible);
    }

    public static WebMarkupContainer createDropDownMenuItem(String pageName, final INavigationPanelAware page) {
        WebMarkupContainer li = new WebMarkupContainer(pageName);
        li.add(new ExternalLink(pageName, "#"));
        if (page != null && page.getActiveTopBarItem().equals(pageName)) {
            li.add(AttributeModifier.append("class", "active"));
        }
        return li;
    }

    public static WebMarkupContainer createMenuItem(String pageName, AbstractLink link, final INavigationPanelAware page) {
        WebMarkupContainer li = new WebMarkupContainer(pageName);
        li.add(link);
        if (page != null && page.getActiveTopBarItem().equals(pageName)) {
            li.add(AttributeModifier.append("class", "active"));
        }
        return li;
    }

    public static class MenuItem implements Serializable {

        public String pageName;

        public Class<? extends Page> targetPageClass;

        public INavigationPanelAware currentPage;

        public boolean visible;

        public MenuItem(final String pageName, final Class<? extends Page> targetPageClass, final INavigationPanelAware currentPage, boolean visible) {
            this.pageName = pageName;
            this.targetPageClass = targetPageClass;
            this.currentPage = currentPage;
            this.visible = visible;
        }
    }
}
