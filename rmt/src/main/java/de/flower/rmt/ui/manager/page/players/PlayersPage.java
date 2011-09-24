package de.flower.rmt.ui.manager.page.players;

import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.User;
import de.flower.rmt.model.User_;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.common.page.ModalDialogWindow;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.Component;
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
public class PlayersPage extends ManagerBasePage {

    @SpringBean
    private IUserManager playerManager;

    @SpringBean
    private ISecurityService securityService;

    public PlayersPage() {

        final ModalDialogWindow modal = new ModalDialogWindow("playerDialog");
        final PlayerEditPanel playerEditPanel = new PlayerEditPanel(modal.getContentId());
        modal.setContent(playerEditPanel);
        add(modal);

        add(new MyAjaxLink("newButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // show modal dialog with edit form.
                playerEditPanel.init(null);
                modal.show(target);
            }
        });

        WebMarkupContainer playerListContainer = new WebMarkupContainer("playerListContainer");
        add(playerListContainer);
        playerListContainer.add(new ListView<User>("playerList", getPlayerListModel()) {


            @Override
            protected void populateItem(final ListItem<User> item) {
                User player = item.getModelObject();
                item.add(new Label("fullname", player.getFullname()));
                item.add(new Label("email", player.getEmail()));
                Component manager;
                item.add(manager = new WebMarkupContainer("manager"));
                manager.setVisible(player.isManager());
                item.add(new MyAjaxLink("editButton") {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        playerEditPanel.init(item.getModel());
                        modal.show(target);
                    }
                });
                AjaxLinkWithConfirmation deleteButton;
                item.add(deleteButton = new AjaxLinkWithConfirmation("deleteButton", new ResourceModel("manager.players.delete.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        playerManager.delete(item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityDeleted(User.class)));
                    }

                });
                deleteButton.setVisible(!securityService.isCurrentUser(player));
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
}
