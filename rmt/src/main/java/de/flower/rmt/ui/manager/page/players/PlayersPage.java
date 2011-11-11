package de.flower.rmt.ui.manager.page.players;

import de.flower.rmt.model.User;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class PlayersPage extends ManagerBasePage {

    public PlayersPage() {

        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new PlayerEditPage());
            }
        });

        add(new PlayerListPanel() {
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
