package de.flower.rmt.ui.manager.page.player;

import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class PlayerEditPage extends ManagerBasePage {

    @SpringBean
    private IUserManager userManager;

    public PlayerEditPage() {
        this(new UserModel());
    }

    public PlayerEditPage(final IModel<User> model) {
        super(model);
        // initialize model object if it is not set
        addHeading("manager.player.edit.heading", null);
        addMainPanel(new PlayerEditPanel(model));

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
        return "players";
    }
}
