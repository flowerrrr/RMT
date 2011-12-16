package de.flower.rmt.ui.manager.page.player;

import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class PlayerPage extends ManagerBasePage {

    @SpringBean
    private IUserManager userManager;

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
        getSecondaryPanel().add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(User.class)));

    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.PLAYERS;
    }
}
