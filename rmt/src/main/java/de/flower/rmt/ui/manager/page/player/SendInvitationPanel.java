package de.flower.rmt.ui.manager.page.player;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class SendInvitationPanel extends BasePanel<User> {

    @SpringBean
    private IUserManager userManager;

    private boolean showFeedbackContainer = false;

    public SendInvitationPanel(final IModel<User> model) {
        super(model);

        final WebMarkupContainer invitationContainer = new WebMarkupContainer("invitationContainer") {
            @Override
            public boolean isVisible() {
                return !showFeedbackContainer;
            }
        };
        add(invitationContainer);
        invitationContainer.add(new AjaxLink("sendButton") {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                userManager.sendInvitation(model.getObject().getId());
                showFeedbackContainer = true;
                target.add(SendInvitationPanel.this);
            }
        });

        final WebMarkupContainer feedbackContainer = new WebMarkupContainer("feedbackContainer") {
            @Override
            public boolean isVisible() {
                return showFeedbackContainer;
            }
        };
        add(feedbackContainer);
        feedbackContainer.add(new AjaxLink("okButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                // hide feedbackContainer
                showFeedbackContainer = false;
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityUpdated(User.class)));
                target.add(SendInvitationPanel.this);
            }
        });
    }

    public boolean isShowFeedbackContainer() {
        return showFeedbackContainer;
    }
}
