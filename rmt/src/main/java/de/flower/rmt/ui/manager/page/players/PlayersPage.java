package de.flower.rmt.ui.manager.page.players;

import de.flower.rmt.model.User;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class PlayersPage extends ManagerBasePage {

    public PlayersPage() {

        addHeading("manager.players.heading", null);

        addSecondaryPanel(new PlayersSecondaryPanel());

        addMainPanel(new PlayerListPanel() {
            @Override
            protected void onEdit(final AjaxRequestTarget target, final IModel<User> model) {
                setResponsePage(new PlayerEditPage(model));
            }
        });

    }


    @Override
    public String getActiveTopBarItem() {
        return "players";
    }

}
