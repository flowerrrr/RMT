package de.flower.rmt.ui.page.teams.manager;

import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import de.flower.rmt.ui.page.base.manager.NavigationPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class TeamsPage extends ManagerBasePage {

    @SpringBean
    private ITeamManager teamManager;

    public TeamsPage() {
        setHeading("manager.teams.heading", null);
        addMainPanel(new TeamListPanel());
        addSecondaryPanel(new TeamsSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.TEAMS;
    }

}
