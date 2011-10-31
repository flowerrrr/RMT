package de.flower.rmt.ui.manager.page.squad;

import de.flower.common.ui.FeatureNotImplementedLink;
import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.js.JQuery;
import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;
import de.flower.rmt.service.IPlayerManager;
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
 * @author flowerrrr
 */
public class SquadPage extends ManagerBasePage {

    @SpringBean
    private ITeamManager teamManager;

    @SpringBean
    private IPlayerManager playerManager;

    private AjaxSlideTogglePanel addPlayerPanel;

    public SquadPage(final LoadableDetachableModel<Team> model) {
        super();

        add(new Label("teamName", model.getObject().getName()));

        final MyAjaxLink addButton = new MyAjaxLink("addButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // show inline  dialog with squad edit form.
                addPlayerPanel.show(target);
            }
       };
        add(addButton);

        addPlayerPanel = new AjaxSlideTogglePanel("addPlayerPanel", new AddPlayerPanel(model)) {
            @Override
            public void onHide(AjaxRequestTarget target) {
                target.prependJavaScript(JQuery.fadeIn(addButton, "slow"));
            }
        };
        add(addPlayerPanel);

        final IModel<List<Player>> listModel = getListModel(model);

        WebMarkupContainer playerListContainer = new WebMarkupContainer("playerListContainer");
        add(playerListContainer);
        playerListContainer.add(new WebMarkupContainer("noPlayer") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        });
        playerListContainer.add(new ListView<Player>("playerList", listModel) {

            @Override
            public boolean isVisible() {
                return !getList().isEmpty();
            }

            @Override
            protected void populateItem(final ListItem<Player> item) {
                final Player player = item.getModelObject();
                item.add(new Label("name",  player.getFullname()));
                item.add(new Label("status", new ResourceModel("player.status." + player.getStatus().toString().toLowerCase())));
                item.add(new FeatureNotImplementedLink("editButton", "Editing players"));
                item.add(new AjaxLinkWithConfirmation("removeButton", new ResourceModel("manager.squad.remove.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        teamManager.removePlayer((Team) SquadPage.this.getDefaultModelObject(), item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityDeleted(Player.class)));
                    }
                });
            }
        });
        playerListContainer.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Player.class)));

    }

    private IModel<List<Player>> getListModel(final IModel<Team> model) {
        return new LoadableDetachableModel<List<Player>>() {
            @Override
            protected List<Player> load() {
                return playerManager.findByTeam(model.getObject());
            }
        };
    }

}
