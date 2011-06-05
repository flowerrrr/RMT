package de.flower.rmt.ui.common.page.login;

import de.flower.rmt.ui.manager.ManagerHomePage;
import de.flower.rmt.ui.player.PlayerHomePage;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.request.component.IRequestablePage;



/**
 * @author oblume
 */
public class HomePageResolver {

    /**
     * Determine if user is manager or player and redirect to appropriate home page.
     * @param application
     */
    public static Class<? extends IRequestablePage> getHomePage(AuthenticatedWebSession session) {
        // get roles
        if (session.getRoles().hasRole(Role.MANAGER.getRoleName())) {
            return ManagerHomePage.class;
        } else {
            return PlayerHomePage.class;
        }

        // map to homepage
    }
}
