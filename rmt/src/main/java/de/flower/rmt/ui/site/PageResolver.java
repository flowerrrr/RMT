package de.flower.rmt.ui.site;

import de.flower.rmt.model.db.entity.Role;
import de.flower.rmt.ui.app.Authentication;
import de.flower.rmt.ui.app.IPageResolver;
import de.flower.rmt.ui.page.error.AccessDenied403Page;
import de.flower.rmt.ui.page.error.InternalError500Page;
import de.flower.rmt.ui.page.error.PageExpiredPage;
import de.flower.rmt.ui.page.error.PageNotFound404Page;
import de.flower.rmt.ui.page.events.player.EventsPage;
import de.flower.rmt.ui.page.login.LoginPage;
import org.apache.wicket.Page;
import org.apache.wicket.request.component.IRequestablePage;
import org.springframework.stereotype.Component;

/**
 * @author flowerrrr
 */
@Component
public class PageResolver implements IPageResolver {

    /**
     * Determine if user is manager or player and redirect to appropriate home page.
     */
    @Override
    public Class<? extends IRequestablePage> getHomePage() {
        // get roles
        if (Authentication.hasRole(Role.Roles.MANAGER.getRoleName())) {
            return de.flower.rmt.ui.page.events.manager.EventsPage.class;
        } else if (Authentication.hasRole(Role.Roles.PLAYER.getRoleName())){
            return EventsPage.class;
        } else {
            return LoginPage.class;
        }
    }

    @Override
    public Class<? extends Page> getPageNotFoundPage() {
        return PageNotFound404Page.class;
    }

    @Override
    public Class<? extends Page> getInternalErrorPage() {
        return InternalError500Page.class;
    }

    @Override
    public Class<? extends Page> getAccessDeniedPage() {
        return AccessDenied403Page.class;
    }

    @Override
    public Class<? extends Page> getPageExpiredPage() {
        return PageExpiredPage.class;
    }
}
