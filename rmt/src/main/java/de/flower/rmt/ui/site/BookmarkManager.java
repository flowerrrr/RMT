package de.flower.rmt.ui.site;

import de.flower.rmt.ui.page.about.AboutPage;
import de.flower.rmt.ui.page.about.ChangeLogPage;
import de.flower.rmt.ui.page.account.AccountPage;
import de.flower.rmt.ui.page.blog.ArticlePage;
import de.flower.rmt.ui.page.blog.BlogPage;
import de.flower.rmt.ui.page.calendar.CalendarPage;
import de.flower.rmt.ui.page.event.manager.EventPage;
import de.flower.rmt.ui.page.events.manager.EventsPage;
import de.flower.rmt.ui.page.login.LoginPage;
import de.flower.rmt.ui.page.login.PasswordForgottenPage;
import de.flower.rmt.ui.page.opponents.manager.OpponentsPage;
import de.flower.rmt.ui.page.teams.manager.TeamsPage;
import de.flower.rmt.ui.page.users.UsersPage;
import de.flower.rmt.ui.page.venues.manager.VenuesPage;
import de.flower.rmt.ui.page.venues.player.VenuePage;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.stereotype.Component;


@Component
public class BookmarkManager {

    public void initBookmarkablePages(final WebApplication webApplication) {
        webApplication.mountPage("manager", EventsPage.class);
        webApplication.mountPage("manager/teams", TeamsPage.class);
        webApplication.mountPage("manager/events", EventsPage.class);
        webApplication.mountPage("manager/event/${" + EventPage.PARAM_EVENTID + "}", EventPage.class);
        webApplication.mountPage("manager/opponents", OpponentsPage.class);
        webApplication.mountPage("manager/venues", VenuesPage.class);
        webApplication.mountPage("events", de.flower.rmt.ui.page.events.player.EventsPage.class);
        webApplication.mountPage("event/${" + EventPage.PARAM_EVENTID + "}", de.flower.rmt.ui.page.event.player.EventPage.class);
        webApplication.mountPage("calendar", CalendarPage.class);
        webApplication.mountPage("blog", BlogPage.class);
        webApplication.mountPage("blog/${" + ArticlePage.PARAM_ARTICLEID + "}", ArticlePage.class);
        webApplication.mountPage("users", UsersPage.class);
        webApplication.mountPage("venues", de.flower.rmt.ui.page.venues.player.VenuesPage.class);
        webApplication.mountPage("venue/${" + VenuePage.PARAM_VENUEID + "}", VenuePage.class);
        webApplication.mountPage("account", AccountPage.class);
        webApplication.mountPage("login/passwordforgotten", PasswordForgottenPage.class);
        webApplication.mountPage("login", LoginPage.class);
        webApplication.mountPage("about", AboutPage.class);
        webApplication.mountPage("changelog", ChangeLogPage.class);
    }

}
