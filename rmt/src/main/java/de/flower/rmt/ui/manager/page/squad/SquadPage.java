package de.flower.rmt.ui.manager.page.squad;

import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.Event;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.Team2Player;
import de.flower.rmt.model.Users;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
public class SquadPage extends ManagerBasePage {

    @SpringBean
    private ITeamManager teamManager;

    private AddPlayerPanel addPlayerPanel;

    public SquadPage(final IModel<Team> model) {
        super(model);


        final MyAjaxLink addButton = new MyAjaxLink("addButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // show inline  dialog with squad edit form.
                addPlayerPanel.setVisible(true);
                target.add(addPlayerPanel);
                target.add(this);
            }

            @Override
            public boolean isVisible() {
                return !addPlayerPanel.isVisible();
            }
        };
        addButton.setOutputMarkupPlaceholderTag(true);
        add(addButton);

        add(addPlayerPanel = new AddPlayerPanel("addPlayerPanel", model) {
            @Override
            public void onClose(AjaxRequestTarget target) {
                target.add(addButton);
            }
        });
        addPlayerPanel.setOutputMarkupPlaceholderTag(true);
        addPlayerPanel.setVisible(false);
        addPlayerPanel.add(new AjaxUpdateBehavior(Event.EntityAll(Team2Player.class)));

        WebMarkupContainer playerListContainer = new WebMarkupContainer("playerListContainer");
        add(playerListContainer);
        playerListContainer.add(new WebMarkupContainer("noPlayer") {
            @Override
            public boolean isVisible() {
                return getListModel(model).getObject().isEmpty();
            }
        });
        playerListContainer.add(new ListView<Users>("playerList", getListModel(model)) {

            @Override
            public boolean isVisible() {
                return !getList().isEmpty();
            }

            @Override
            protected void populateItem(final ListItem<Users> item) {
                item.add(new Label("name", item.getModelObject().getFullname()));
                item.add(new Label("status", new ResourceModel("player.status." + item.getModelObject().getStatus().toString().toLowerCase())));
                item.add(new MyAjaxLink("editButton") {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        throw new UnsupportedOperationException("Feature not implemented!");
                    }
                });
                item.add(new AjaxLinkWithConfirmation("removeButton", new ResourceModel("manager.squad.remove.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        teamManager.removePlayer((Team) SquadPage.this.getDefaultModelObject(), item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(Event.EntityDeleted(Team2Player.class)));
                    }
                });
            }
        });
        playerListContainer.add(new AjaxUpdateBehavior(Event.EntityAll(Team2Player.class)));

    }

    private IModel<List<Users>> getListModel(final IModel<Team> model) {
        return new LoadableDetachableModel<List<Users>>() {
            @Override
            protected List<Users> load() {
                return teamManager.getPlayers(model.getObject());
            }
        };
    }

}
