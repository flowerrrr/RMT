package de.flower.rmt.ui.app;

import de.flower.rmt.ui.common.page.login.HomePageResolver;
import de.flower.rmt.ui.common.page.login.LoginPage;
import de.flower.rmt.ui.manager.ManagerHomePage;
import de.flower.rmt.ui.manager.page.myteams.MyTeamsPage;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component(value = "wicketApplication")
public class Application extends AuthenticatedWebApplication {

    private static final String DEFAULT_ENCODING = "UTF-8";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void init() {
        super.init();
        // add support for @SpringBean
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        initBookmarkablePages();
    }

    private void initBookmarkablePages() {
        mountPage("manager", ManagerHomePage.class);
        mountPage("manager/myteams", MyTeamsPage.class);
        // mountPage("manager/players", null);
        mountPage("login", LoginPage.class);
    }

    @Override
    protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
        return WebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }


    @Override
    public Class getHomePage() {
        return HomePageResolver.getHomePage(WebSession.get());
    }


}