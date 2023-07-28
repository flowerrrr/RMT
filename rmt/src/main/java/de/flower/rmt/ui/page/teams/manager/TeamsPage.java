package de.flower.rmt.ui.page.teams.manager;

import de.flower.rmt.service.TeamManager;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class TeamsPage extends ManagerBasePage {

    @SpringBean
    private TeamManager teamManager;

    public TeamsPage() {
        setHeading("manager.teams.heading", null);
        addMainPanel(new TeamListPanel());
        addSecondaryPanel(new TeamsSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return Pages.TEAMS.name();
    }

}
