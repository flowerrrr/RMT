package de.flower.rmt.ui.page.user.manager;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.UserManager;
import de.flower.rmt.ui.markup.html.panel.SimpleFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class ResetPasswordPanel extends BasePanel<User> {

    @SpringBean
    private UserManager userManager;

    public ResetPasswordPanel(final IModel<User> model) {
        super(model);

        add(new SimpleFeedbackPanel(new ComponentFeedbackMessageFilter(this)));

        add(new AjaxLink("resetButton") {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                userManager.resetPassword(model.getObject(), true);
                // generate feedback message
                addInfoMessage(model);
                target.add(ResetPasswordPanel.this);
            }
        });
    }

    private void addInfoMessage(final IModel<User> model) {
        String msg = new ResourceModel("manager.player.resetpassword.success").getObject();
        info(msg);
        msg = new StringResourceModel("manager.player.resetpassword.newpassword",
                this,
                null,
                new Object[] { model.getObject().getInitialPassword() }).getString();
        info(msg);
    }
}
