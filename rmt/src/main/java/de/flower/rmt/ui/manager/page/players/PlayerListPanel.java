package de.flower.rmt.ui.manager.page.players;

import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.tooltips.TwipsyBehavior;
import de.flower.rmt.model.User;
import de.flower.rmt.model.User_;
import de.flower.rmt.service.IRoleManager;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.common.panel.DropDownMenuPanel;
import de.flower.rmt.ui.manager.page.player.PlayerEditPage;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.Component;
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
public class PlayerListPanel extends BasePanel {

    @SpringBean
    private IUserManager playerManager;

    @SpringBean
    private IRoleManager roleManager;

    @SpringBean
    private ISecurityService securityService;

    public PlayerListPanel() {

        WebMarkupContainer playerListContainer = new WebMarkupContainer("listContainer");
        add(playerListContainer);
        playerListContainer.add(new ListView<User>("playerList", getPlayerListModel()) {

            @Override
            protected void populateItem(final ListItem<User> item) {
                User player = item.getModelObject();
                Link editLink = createEditLink("editLink", item);
                editLink.add(new Label("fullname", player.getFullname()));
                item.add(editLink);
                item.add(new Label("email", player.getEmail()));
                Component manager;
                item.add(manager = new WebMarkupContainer("manager"));
                manager.setVisible(player.isManager());

                Link sendInvitiationLink = new Link("sendInvitationLink") {
                    @Override
                    public void onClick() {
                        setResponsePage(new PlayerEditPage(new UserModel(item.getModel())));
                    }
                };
                sendInvitiationLink.setVisible(!player.isInvitationSent());
                item.add(sendInvitiationLink);
                sendInvitiationLink.add(new TwipsyBehavior(new ResourceModel("manager.players.tooltip.invitiation.not.send")));

                // now the dropdown menu
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                menuPanel.addLink(createEditLink("link", item), "button.edit");
                AjaxLinkWithConfirmation deleteButton;
                menuPanel.addLink(deleteButton = new AjaxLinkWithConfirmation("link", new ResourceModel("manager.players.delete.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        playerManager.delete(item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityDeleted(User.class)));
                    }
                }, "button.delete");
                deleteButton.setVisible(!securityService.isCurrentUser(player));
                item.add(menuPanel);
            }

        });
        playerListContainer.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(User.class)));
    }

    private IModel<List<User>> getPlayerListModel() {
        return new LoadableDetachableModel<List<User>>() {
            @Override
            protected List<User> load() {
                return playerManager.findAll(User_.roles);
            }
        };
    }

    private Link createEditLink(String id, final ListItem<User> item) {
        return new Link(id) {
            @Override
            public void onClick() {
                setResponsePage(new PlayerEditPage(new UserModel(item.getModel())));
            }
        };
    }
}
