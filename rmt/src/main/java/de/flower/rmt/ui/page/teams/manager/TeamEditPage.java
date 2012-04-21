package de.flower.rmt.ui.page.teams.manager;

import de.flower.rmt.model.Team;
import de.flower.rmt.ui.model.TeamModel;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import de.flower.rmt.ui.page.base.manager.NavigationPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class TeamEditPage extends ManagerBasePage {

    public TeamEditPage() {
        this(new TeamModel());
    }

    public TeamEditPage(IModel<Team> model) {
        setHeading("manager.team.edit.heading", null);
        addMainPanel(new TeamEditPanel(model) {
            @Override
            protected void onClose(AjaxRequestTarget target) {
                setResponsePage(TeamsPage.class);
            }
        });
        // addSecondaryPanel(new Label("foobar", "Put some useful stuff here."));
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.TEAMS;
    }

}