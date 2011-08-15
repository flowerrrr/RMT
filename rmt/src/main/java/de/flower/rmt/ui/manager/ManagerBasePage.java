package de.flower.rmt.ui.manager;

import de.flower.rmt.model.Team;
import de.flower.rmt.ui.app.AbstractBasePage;
import org.apache.wicket.model.IModel;

/**
 * @author oblume
 */
public class ManagerBasePage extends AbstractBasePage {

    public ManagerBasePage() {
        this(null);
    }

    public ManagerBasePage(IModel<Team> model) {
        super(model);
        add(new NavigationPanel("navPanel"));
    }
}
