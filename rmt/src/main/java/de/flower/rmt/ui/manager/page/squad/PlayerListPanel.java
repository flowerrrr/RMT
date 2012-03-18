package de.flower.rmt.ui.manager.page.squad;

import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.markup.html.tab.AbstractAjaxTabbedPanel;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.rmt.model.Player;
import de.flower.rmt.model.Player_;
import de.flower.rmt.model.Team;
import de.flower.rmt.service.IPlayerManager;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.common.panel.DropDownMenuPanel;
import de.flower.rmt.ui.manager.page.player.PlayerMainPanel;
import de.flower.rmt.ui.manager.page.player.PlayerPage;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class PlayerListPanel extends BasePanel<Team> {

    @SpringBean
    private ITeamManager teamManager;

    @SpringBean
    private IPlayerManager playerManager;

    public PlayerListPanel(IModel<Team> model) {
        super(model);

        final IModel<List<Player>> listModel = getListModel(model);

        WebMarkupContainer playerListContainer = new WebMarkupContainer("listContainer");
        add(playerListContainer);
        playerListContainer.add(new WebMarkupContainer("noPlayer") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        });
        playerListContainer.add(new EntityListView<Player>("playerList", listModel) {

            @Override
            public boolean isVisible() {
                return !getList().isEmpty();
            }

            @Override
            protected void populateItem(final ListItem<Player> item) {
                final Player player = item.getModelObject();
                Link editLink = createEditLink("editLink", item);
                editLink.add(new Label("fullname", player.getFullname()));
                item.add(editLink);
                item.add(new Label("status", new ResourceModel("player.status." + player.getUser().getStatus().toString().toLowerCase())));
                item.add(new Label("notification", new ResourceModel("choice.player.notification." + player.isNotification().toString())));
                item.add(new Label("response", new ResourceModel("choice.player.response.optional." + player.isOptional().toString())));
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                item.add(menuPanel);
                menuPanel.addLink(createEditLink("link", item), "button.edit");
                menuPanel.addLink(new AjaxLinkWithConfirmation("link", new ResourceModel("manager.squad.remove.confirm")) {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        teamManager.removePlayer(PlayerListPanel.this.getModelObject(), item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityDeleted(Player.class)));
                    }
                }, "button.squad.player.remove");
            }
        });
        playerListContainer.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Player.class)));
    }

    private IModel<List<Player>> getListModel(final IModel<Team> model) {
        return new LoadableDetachableModel<List<Player>>() {
            @Override
            protected List<Player> load() {
                return playerManager.findAllByTeam(model.getObject(), Player_.user);
            }
        };
    }

    private Link createEditLink(String id, final ListItem<Player> item) {
        return new Link(id) {
            @Override
            public void onClick() {
                PlayerPage page = new PlayerPage(new UserModel(item.getModelObject().getUser()));
                page.getPageParameters().set(AbstractAjaxTabbedPanel.TAB_INDEX_KEY, PlayerMainPanel.TEAM_SETTINGS_PANEL_INDEX);
                setResponsePage(page);
            }
        };
    }
}
