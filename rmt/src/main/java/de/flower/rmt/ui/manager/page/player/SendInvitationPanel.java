package de.flower.rmt.ui.manager.page.player;

import de.flower.common.ui.ajax.AjaxLink;
import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.form.SimpleFeedbackPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class SendInvitationPanel extends BasePanel<User> {

    @SpringBean
    private IUserManager userManager;

    public SendInvitationPanel(final IModel<User> model) {
        super(model);

        add(new SimpleFeedbackPanel(new ComponentFeedbackMessageFilter(this)));

        add(new AjaxLink("sendButton") {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                userManager.sendInvitation(model.getObject().getId());
                // generate feedback message
                addInfoMessage(model);
                target.add(SendInvitationPanel.this);
            }
        });
    }

    private void addInfoMessage(final IModel<User> model) {
        String msg = new ResourceModel("manager.player.invitation.success").getObject();
        info(msg);
    }
}
