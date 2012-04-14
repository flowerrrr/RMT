package de.flower.rmt.ui.page.user.manager;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.rmt.model.User;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import de.flower.rmt.ui.page.base.manager.NavigationPanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class PlayerPage extends ManagerBasePage {

    public PlayerPage() {
        // for creating new player
        this(new UserModel());
    }

    public PlayerPage(final IModel<User> model) {
        super(model);
        // initialize model object if it is not set
        setHeading("manager.player.edit.heading");
        // replace main heading with players name.
        setHeadingText(model.getObject().getFullname());
        addMainPanel(new PlayerMainPanel(model));

        final SendInvitationPanel sendInvitationPanel = new SendInvitationPanel(model) {
            @Override
            public boolean isVisible() {
                return (!model.getObject().isNew()
                        && !model.getObject().isInvitationSent()
                        && model.getObject().getInitialPassword() != null);
            }
        };

        ResetPasswordPanel resetPasswordPanel = new ResetPasswordPanel(model) {
            @Override
            public boolean isVisible() {
                // only show if existing user is edited.
                // never show no invitation mail has been sent. doesn't make sense to display both panels.
                return !model.getObject().isNew();
            }
        };
        addSecondaryPanel(sendInvitationPanel, resetPasswordPanel);
        // listen to events when user is created.
        getSecondaryPanel().add(new AjaxEventListener(User.class));

    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.PLAYERS;
    }
}
