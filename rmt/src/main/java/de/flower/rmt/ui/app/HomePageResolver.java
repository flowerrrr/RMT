package de.flower.rmt.ui.app;

import de.flower.rmt.model.db.entity.Role;
import de.flower.rmt.ui.page.events.player.EventsPage;
import de.flower.rmt.ui.page.login.LoginPage;
import org.apache.wicket.request.component.IRequestablePage;


/**
 * @author flowerrrr
 */
public class HomePageResolver {

    /**
     * Determine if user is manager or player and redirect to appropriate home page.
     *
     */
    public static Class<? extends IRequestablePage> getHomePage() {
        // get roles
        if (Authentication.hasRole(Role.Roles.MANAGER.getRoleName())) {
            return de.flower.rmt.ui.page.events.manager.EventsPage.class;
        } else if (Authentication.hasRole(Role.Roles.PLAYER.getRoleName())){
            return EventsPage.class;
        } else {
            return LoginPage.class;
        }
    }

}
