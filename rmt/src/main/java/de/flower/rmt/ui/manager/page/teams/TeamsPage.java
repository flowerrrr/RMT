package de.flower.rmt.ui.manager.page.teams;

import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class TeamsPage extends ManagerBasePage {

    @SpringBean
    private ITeamManager teamManager;

    public TeamsPage() {
        addHeading("manager.teams.heading", null);
        addMainPanel(new TeamListPanel());
        addSecondaryPanel(new TeamsSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return "teams";
    }

}
