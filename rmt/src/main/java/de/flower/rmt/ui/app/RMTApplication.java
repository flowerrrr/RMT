package de.flower.rmt.ui.app;

import de.flower.common.ui.serialize.Filter;
import de.flower.common.ui.serialize.LoggingSerializer;
import de.flower.common.ui.serialize.SerializerWrapper;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.type.Notification;
import de.flower.rmt.model.type.Password;
import de.flower.rmt.ui.common.page.account.AccountPage;
import de.flower.rmt.ui.common.page.login.LoginPage;
import de.flower.rmt.ui.manager.ManagerHomePage;
import de.flower.rmt.ui.manager.page.event.EventEditPage;
import de.flower.rmt.ui.manager.page.events.EventsPage;
import de.flower.rmt.ui.manager.page.opponents.OpponentsPage;
import de.flower.rmt.ui.manager.page.players.PlayersPage;
import de.flower.rmt.ui.manager.page.teams.TeamsPage;
import de.flower.rmt.ui.manager.page.venues.VenuesPage;
import de.flower.rmt.ui.player.PlayerHomePage;
import de.flower.rmt.ui.player.page.event.EventPage;
import org.apache.wicket.devutils.inspector.RenderPerformanceListener;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.serialize.ISerializer;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "wicketApplication")
public class RMTApplication extends WebApplication {

    private final static Logger log = LoggerFactory.getLogger(RMTApplication.class);

    @Override
    protected void init() {
        super.init();
        // add support for @SpringBean
        getComponentInstantiationListeners().add(getSpringComponentInjector());
        // google maps have problems when wicket tags are rendered in development mode, so strip those tags
        getMarkupSettings().setStripWicketTags(true);

        if (usesDevelopmentConfig()) {
            getDebugSettings().setDevelopmentUtilitiesEnabled(true);
            getDebugSettings().setOutputComponentPath(true);
            getDebugSettings().setOutputMarkupContainerClassName(true);

            getComponentInstantiationListeners().add(new RenderPerformanceListener());
            initSerializer();
        }

        initBookmarkablePages();

    }

    /**
     * Adds a listener that analyses and logs the serialized pages to find out
     * if unwanted objects (e.g. domain objects) are serialized.
     */
    private void initSerializer() {
        final ISerializer serializer = getFrameworkSettings().getSerializer();
        SerializerWrapper wrapper = new SerializerWrapper(serializer);
        Filter filter = new Filter("\"de\\.flower\\.rmt\\.model\\.[^-]*?\"");
        filter.addExclusion(RSVPStatus.class.getName());
        filter.addExclusion(Password.class.getName());
        filter.addExclusion(Notification.class.getName());
        wrapper.addListener(new LoggingSerializer(filter));
        getFrameworkSettings().setSerializer(wrapper);
    }

    protected SpringComponentInjector getSpringComponentInjector() {
        return new SpringComponentInjector(this);
    }

    private void initBookmarkablePages() {
        mountPage("manager", ManagerHomePage.class);
        mountPage("manager/teams", TeamsPage.class);
        mountPage("manager/players", PlayersPage.class);
        mountPage("manager/events", EventsPage.class);
        mountPage("manager/event/${" + EventPage.PARAM_EVENTID + "}", EventEditPage.class);
        mountPage("manager/opponents", OpponentsPage.class);
        mountPage("manager/venues", VenuesPage.class);
        mountPage("player", PlayerHomePage.class);
        mountPage("player/events", de.flower.rmt.ui.player.page.events.EventsPage.class);
        mountPage("player/event/${" + EventPage.PARAM_EVENTID + "}", EventPage.class);
        mountPage("common/account", AccountPage.class);
        mountPage("login", LoginPage.class);
    }

    @Override
    public Class getHomePage() {
        return HomePageResolver.getHomePage();
    }

    @Override
    public RMTSession newSession(Request request, Response response) {
        return new RMTSession(request);
    }

    /**
     * Output to log instead of System.err.
     */
    @Override
    protected void outputDevelopmentModeWarning()
    {
        log.warn("\n********************************************************************\n"
                + "*** WARNING: Wicket is running in DEVELOPMENT mode.              ***\n"
                + "***                               ^^^^^^^^^^^                    ***\n"
                + "*** Do NOT deploy to your live server(s) without changing this.  ***\n"
                + "*** See Application#getConfigurationType() for more information. ***\n"
                + "********************************************************************\n");
    }
            
}