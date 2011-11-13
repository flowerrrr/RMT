package de.flower.rmt.ui.manager.page.teams;

import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Team;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.common.panel.DropDownMenuPanel;
import de.flower.rmt.ui.manager.page.squad.SquadPage;
import de.flower.rmt.ui.model.TeamModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class TeamListPanel extends BasePanel {

    @SpringBean
    private ITeamManager teamManager;

    public TeamListPanel() {
        super();

        WebMarkupContainer teamListContainer = new WebMarkupContainer("listContainer");
        add(teamListContainer);
        teamListContainer.add(new ListView<Team>("teamList", getTeamListModel()) {

            @Override
            protected void populateItem(final ListItem<Team> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new Link("squadButton") {
                    @Override
                    public void onClick() {
                        setResponsePage(new SquadPage(new TeamModel(item.getModelObject())));
                    }
                });
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                item.add(menuPanel);
                menuPanel.add(new Link("editButton") {
                    @Override
                    public void onClick() {
                        setResponsePage(new TeamEditPage(new TeamModel(item.getModelObject())));
                    }
                });
                menuPanel.add(new AjaxLinkWithConfirmation("deleteButton", new ResourceModel("manager.teams.delete.confirm")) {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        teamManager.delete(item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityDeleted(Team.class)));
                    }
                });
            }
        });
        teamListContainer.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Team.class)));
    }

    private IModel<List<Team>> getTeamListModel() {
        return new LoadableDetachableModel<List<Team>>() {
            @Override
            protected List<Team> load() {
                return teamManager.findAll();
            }
        };
    }
}
