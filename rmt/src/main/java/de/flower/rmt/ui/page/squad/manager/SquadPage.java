package de.flower.rmt.ui.page.squad.manager;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.ui.page.base.NavigationPanel;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class SquadPage extends ManagerBasePage {

    public SquadPage(final IModel<Team> model) {
        setHeadingText(model.getObject().getName());
        setSubheadingText(new ResourceModel("squad.heading.sub").getObject());
        addMainPanel(new PlayerListPanel(model));
        addSecondaryPanel(new SquadSecondaryPanel(model));
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.TEAMS;
    }


}
