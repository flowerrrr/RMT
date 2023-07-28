package de.flower.rmt.ui.page.user.manager;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.UserManager;
import de.flower.rmt.ui.markup.html.panel.SimpleFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class SendInvitationPanel extends BasePanel<User> {

    @SpringBean
    private UserManager userManager;

    public SendInvitationPanel(final IModel<User> model) {
        super(model);

        add(new SimpleFeedbackPanel(new ComponentFeedbackMessageFilter(this)));

        final WebMarkupContainer container = new WebMarkupContainer("container");
        add(container);
        container.add(new AjaxLink("sendButton") {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                userManager.sendInvitation(model.getObject().getId());
                // generate feedback message
                addInfoMessage(model);
                container.setVisible(false);
                target.add(SendInvitationPanel.this);
            }
        });
    }

    private void addInfoMessage(final IModel<User> model) {
        String msg = new ResourceModel("manager.player.invitation.success").getObject();
        info(msg);
    }
}
