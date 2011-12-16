package de.flower.rmt.ui.manager.page.squad;

import de.flower.rmt.model.Team;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class SquadPage extends ManagerBasePage {

    public SquadPage(final IModel<Team> model) {
        super();
        setHeadingText(model.getObject().getName());
        addMainPanel(new PlayerListPanel(model));
        addSecondaryPanel(new SquadSecondaryPanel(model));

   }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.TEAMS;
    }


}
