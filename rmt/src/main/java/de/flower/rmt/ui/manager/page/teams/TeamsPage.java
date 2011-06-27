package de.flower.rmt.ui.manager.page.teams;

import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.Event;
import de.flower.rmt.model.Team;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.common.page.ModalDialogWindow;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author oblume
 */
public class TeamsPage extends ManagerBasePage {

    @SpringBean
    private ITeamManager teamManager;

    public TeamsPage() {

        final ModalDialogWindow modal = new ModalDialogWindow("teamDialog");
        final TeamEditPanel teamEditPanel = new TeamEditPanel(modal.getContentId());
        modal.setContent(teamEditPanel);
        add(modal);

        add(new AjaxLink("newButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // show modal dialog with team edit form.
                teamEditPanel.init(null);
                modal.show(target);
            }
        });

        WebMarkupContainer teamListContainer = new WebMarkupContainer("teamListContainer");
        add(teamListContainer);
        teamListContainer.add(new ListView<Team>("teamList", getTeamListModel()) {

            @Override
            protected void populateItem(final ListItem<Team> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new AjaxLink("editButton") {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        teamEditPanel.init(item.getModel());
                        modal.show(target);
                    }
                });
                item.add(new AjaxLinkWithConfirmation("deleteButton", new ResourceModel("manager.myteams.delete.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        teamManager.delete(item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(Event.EntityDeleted(Team.class)));
                    }
                });
            }
        });
        teamListContainer.add(new AjaxUpdateBehavior(Event.EntityAll(Team.class)));
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
