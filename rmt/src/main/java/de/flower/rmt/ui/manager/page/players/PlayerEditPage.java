package de.flower.rmt.ui.manager.page.players;

import de.flower.rmt.model.User;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class PlayerEditPage extends ManagerBasePage {

    public PlayerEditPage() {
        this(new UserModel());
    }

    public PlayerEditPage(IModel<User> model) {
        super(model);
        addHeading("manager.player.heading", null);
        addMainPanel(new PlayerEditPanel(model) {
            @Override
            protected void onClose(final AjaxRequestTarget target) {
                setResponsePage(PlayersPage.class);
            }
        });
        addSecondaryPanel(new Label("foobar", "Put something useful here"));
    }

    @Override
    public String getActiveTopBarItem() {
        return "players";
    }

}
