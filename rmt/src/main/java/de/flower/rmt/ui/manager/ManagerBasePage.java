package de.flower.rmt.ui.manager;

import de.flower.rmt.ui.app.AbstractBasePage;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * @author oblume
 */
@AuthorizeInstantiation("ROLE_MANAGER")
public class ManagerBasePage extends AbstractBasePage {

    public ManagerBasePage() {
        add(new NavigationPanel("navPanel"));
    }

}
