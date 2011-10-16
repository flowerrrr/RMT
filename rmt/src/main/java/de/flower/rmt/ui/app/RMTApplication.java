package de.flower.rmt.ui.app;

import de.flower.common.ui.serialize.LoggingSerializer;
import de.flower.common.ui.serialize.SerializerWrapper;
import de.flower.rmt.ui.common.page.login.LoginPage;
import de.flower.rmt.ui.manager.ManagerHomePage;
import de.flower.rmt.ui.manager.page.events.EventsPage;
import de.flower.rmt.ui.manager.page.players.PlayersPage;
import de.flower.rmt.ui.manager.page.teams.TeamsPage;
import de.flower.rmt.ui.manager.page.venues.VenuesPage;
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

        getDebugSettings().setDevelopmentUtilitiesEnabled(true);

        if (usesDevelopmentConfig()) {
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
        wrapper.addListener(new LoggingSerializer("\"de\\.flower\\.rmt\\.model\\.[^-]*?\""));
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
        mountPage("manager/venues", VenuesPage.class);
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