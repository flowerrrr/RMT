package de.flower.rmt.ui.site;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.QEvent;
import de.flower.rmt.service.BlogManager;
import de.flower.rmt.service.EventManager;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.markup.html.form.renderer.EventRenderer;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.about.AboutPage;
import de.flower.rmt.ui.page.account.AccountPage;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import de.flower.rmt.ui.page.blog.BlogPage;
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
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class NavigationPanel extends RMTBasePanel {

    @SpringBean
    private EventManager eventManager;

    @SpringBean
    private BlogManager blogManager;

    public NavigationPanel(INavigationPanelAware page) {
        setRenderBodyOnly(true);

        add(new BookmarkablePageLink("about", AboutPage.class));

        WebMarkupContainer events = new WebMarkupContainer("events");
        if (isManagerView()) {
            addMenuItem(events, Pages.EVENTS.name(), EventsPage.class, page, true);
        } else {
            addMenuItem(events, Pages.EVENTS.name(), de.flower.rmt.ui.page.events.player.EventsPage.class, page, true);
        }
        add(events);
        events.add(new AjaxEventListener(Event.class));
        final IModel<List<Event>> upcomingEventsModel = getEventListModel();
        events.add(new ListView<Event>("eventList", upcomingEventsModel) {
            @Override
            protected void populateItem(final ListItem<Event> item) {
                Link link;
                if (isManagerView()) {
                    link = new BookmarkablePageLink("link", EventPage.class, EventPage.getPageParams(item.getModelObject().getId(), EventTabPanel.INVITATIONS_PANEL_INDEX));
                } else {
                    link = new BookmarkablePageLink("link", de.flower.rmt.ui.page.event.player.EventPage.class, EventPage.getPageParams(item.getModelObject().getId()));
                }
                link.add(new Label("label", EventRenderer.getDateTeamTypeSummary(item.getModelObject(), false)));
                item.add(link);
            }
        });
        events.add(new WebMarkupContainer("noUpcomingEvents") {
            @Override
            public boolean isVisible() {
                return upcomingEventsModel.getObject().isEmpty();
            }
        });

        WebMarkupContainer blog = new WebMarkupContainer("blog");
        Link link = addMenuItem(blog, Pages.BLOG.name(), BlogPage.class, page, true);
        link.add(new WebMarkupContainer("unreadBadge") {
            @Override
            public boolean isVisible() {
                return blogManager.hasUnreadArticleOrComment(getUser());
            }
        });
        add(blog);

        List<MenuItem> menuItems = new ArrayList<>();
        // menuItems.add(new MenuItem(BLOG, BlogPage.class, page, true));
        menuItems.add(new MenuItem(Pages.CALENDAR.name(), CalendarPage.class, page, true));
        menuItems.add(new MenuItem(Pages.TEAMS.name(), TeamsPage.class, page, getView() == View.MANAGER));
        menuItems.add(new MenuItem(Pages.USERS.name(), UsersPage.class, page, true));
        menuItems.add(new MenuItem(Pages.OPPONENTS.name(), OpponentsPage.class, page, getView() == View.MANAGER));
        // TODO (flowerrrr - 24.06.12) unify both pages
        menuItems.add(new MenuItem(Pages.VENUES.name(), VenuesPage.class, page, getView() == View.MANAGER));
        menuItems.add(new MenuItem(Pages.VENUES.name(), de.flower.rmt.ui.page.venues.player.VenuesPage.class, page, getView() != View.MANAGER));

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
                // from yesterday to next three month.
                if (isManagerView()) {
                    return eventManager.findAllByDateRangeAndUser(new DateTime().minusDays(1), new DateTime().plusMonths(3), null, QEvent.event.team, QEvent.event.opponent);
                } else {
                    return eventManager.findAllByDateRangeAndUser(new DateTime().minusDays(1), new DateTime().plusMonths(3), getUser(), QEvent.event.team, QEvent.event.opponent);
                }
            }
        };
    }

    /**
     * Depending on user role (manager or player) a link is generated that
     * allows the user to switch between both views.
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
            link = new BookmarkablePageLink(id, EventsPage.class);
            link.setVisible(getUser().isManager());
            link.add(new Label("label", new ResourceModel("navigation.switch.manager").getObject()));
        }
        return link;
    }

    public Link addMenuItem(final WebMarkupContainer item, String pageName, Class<?> pageClass, final INavigationPanelAware page, boolean visible) {
        BookmarkablePageLink link = new BookmarkablePageLink("link", pageClass);
        link.add(new Label("label", new ResourceModel("navigation." + pageName.toLowerCase())));
        item.add(link);
        if (page != null && page.getActiveTopBarItem().equals(pageName)) {
            item.add(AttributeModifier.append("class", "active"));
        }
        item.setVisible(visible);
        return link;
    }

    public static WebMarkupContainer createDropDownMenuItem(String pageName, final INavigationPanelAware page) {
        WebMarkupContainer li = new WebMarkupContainer(pageName);
        li.add(new ExternalLink(pageName, "#"));
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
