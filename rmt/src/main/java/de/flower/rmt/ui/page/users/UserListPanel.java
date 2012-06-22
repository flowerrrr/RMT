package de.flower.rmt.ui.page.users;

import com.google.common.collect.ImmutableList;
import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.ui.tooltips.TooltipBehavior;
import de.flower.rmt.model.db.entity.Player;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.User_;
import de.flower.rmt.service.IRoleManager;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.user.manager.PlayerPage;
import de.flower.rmt.ui.panel.DropDownMenuPanel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author flowerrrr
 */
public class UserListPanel extends RMTBasePanel {

    @SpringBean
    private IUserManager userManager;

    @SpringBean
    private IRoleManager roleManager;

    @SpringBean
    private ISecurityService securityService;

    public UserListPanel() {

        WebMarkupContainer playerListContainer = new WebMarkupContainer("listContainer");
        add(playerListContainer);
        playerListContainer.add(new EntityListView<User>("playerList", getUserListModel()) {

            @Override
            protected void populateItem(final ListItem<User> item) {
                User user = item.getModelObject();
                Link editLink = createEditLink("editLink", item);
                editLink.add(new Label("fullname", user.getFullname()));
                editLink.setEnabled(isManagerView());
                item.add(editLink);

                item.add(createMailLinkList(item.getModel()));

                // list of teams the user belongs to
                item.add(createTeamList(item.getModel()));

                Component manager;
                item.add(manager = new WebMarkupContainer("manager"));
                manager.setVisible(user.isManager());

                Link sendInvitiationLink = new Link("sendInvitationLink") {
                    @Override
                    public void onClick() {
                        setResponsePage(new PlayerPage(new UserModel(item.getModel())));
                    }
                };
                // RMT-426
                sendInvitiationLink.setVisible(isManagerView() && !user.isInvitationSent() && user.hasInitialPassword());
                item.add(sendInvitiationLink);
                sendInvitiationLink.add(new TooltipBehavior(new ResourceModel("manager.players.tooltip.invitiation.not.send")));

                // now the dropdown menu
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                menuPanel.setVisible(isManagerView());
                menuPanel.addLink(createEditLink("link", item), "button.edit");
                AjaxLinkWithConfirmation deleteButton;
                menuPanel.addLink(deleteButton = new AjaxLinkWithConfirmation("link", new ResourceModel("manager.players.delete.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        userManager.delete(item.getModelObject().getId());
                        AjaxEventSender.entityEvent(this, User.class);
                    }
                }, "button.delete");
                deleteButton.setVisible(!securityService.isCurrentUser(user));
                item.add(menuPanel);
            }
        });
        playerListContainer.add(new AjaxEventListener(User.class));
    }

    private ListView createMailLinkList(final IModel<User> userModel) {
        return new ListView<InternetAddress>("emails", ImmutableList.copyOf(userModel.getObject().getInternetAddresses())) {
            @Override
            protected void populateItem(final ListItem<InternetAddress> item) {
                String email = item.getModelObject().getAddress();
                item.add(Links.mailLink("emailLink", email, email));
            }
        };
    }

    private ListView createTeamList(final IModel<User> userModel) {
        IModel<List<Player>> playerListModel = new AbstractReadOnlyModel<List<Player>>() {
            @Override
            public List<Player> getObject() {
                // because of pre-fetching multiple associations the list of players can contain
                // duplicate entries (e.g. when user has two roles assigned).
                // filter out duplicate entries
                return new ArrayList(new HashSet(userModel.getObject().getPlayers()));
            }
        };
        return new ListView<Player>("teams", playerListModel) {
            @Override
            protected void populateItem(final ListItem<Player> item) {
                item.add(new Label("team", item.getModelObject().getTeam().getName()));
            }
        };
    }

    private IModel<List<User>> getUserListModel() {
        return new LoadableDetachableModel<List<User>>() {
            @Override
            protected List<User> load() {
                return userManager.findAllFetchTeams(User_.roles);
            }
        };
    }

    private Link createEditLink(String id, final ListItem<User> item) {
        return new Link(id) {
            @Override
            public void onClick() {
                setResponsePage(new PlayerPage(new UserModel(item.getModel())));
            }
        };
    }
}
