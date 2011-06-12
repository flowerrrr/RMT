package de.flower.rmt.ui.app;

import de.flower.rmt.ui.common.page.login.HomePageResolver;
import de.flower.rmt.ui.common.page.login.LoginPage;
import de.flower.rmt.ui.manager.ManagerHomePage;
import de.flower.rmt.ui.manager.page.myteams.MyTeamsPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;

@Component(value = "wicketApplication")
public class Application extends WebApplication {

    @Override
    protected void init() {
        super.init();
        // add support for @SpringBean
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        initBookmarkablePages();
    }

    private void initBookmarkablePages() {
        // TODO (oblume - 12.06.11) determine correct order
        mountPackage("manager", ManagerHomePage.class);
        mountPage("manager", ManagerHomePage.class);
        mountPage("manager/myteams", MyTeamsPage.class);
        // mountPage("manager/players", null);
        mountPage("login", LoginPage.class);
    }

    @Override
    public Class getHomePage() {
        return HomePageResolver.getHomePage();
    }


}