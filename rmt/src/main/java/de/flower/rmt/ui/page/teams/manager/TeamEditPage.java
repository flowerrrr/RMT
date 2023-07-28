package de.flower.rmt.ui.page.teams.manager;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.ui.model.TeamModel;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;


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
        return Pages.TEAMS.name();
    }

}
