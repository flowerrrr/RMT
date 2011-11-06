package de.flower.rmt.ui.manager.page.players;

import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.rmt.model.User;
import de.flower.rmt.ui.common.page.ModalDialogWindow;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class PlayersPage extends ManagerBasePage {

    public PlayersPage() {

        final ModalDialogWindow modal = new ModalDialogWindow("playerDialog");
        add(modal);
        add(new MyAjaxLink("newButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // show modal dialog with edit form.
                modal.setContent(new PlayerEditPanel(modal.getContentId(), new UserModel()));
                modal.show(target);
            }
        });

        add(new PlayerListPanel("playerListPanel") {
            @Override
            protected void onEdit(final AjaxRequestTarget target, final IModel<User> model) {
                modal.setContent(new PlayerEditPanel(modal.getContentId(), model));
                modal.show(target);
            }
        });

    }


    @Override
    public String getActiveTopBarItem() {
        return "players";
    }

}
