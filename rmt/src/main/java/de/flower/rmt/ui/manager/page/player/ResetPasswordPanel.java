package de.flower.rmt.ui.manager.page.player;

import de.flower.common.ui.ajax.AjaxLink;
import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class ResetPasswordPanel extends BasePanel<User> {

    @SpringBean
    private IUserManager userManager;

    private boolean showFeedbackContainer = false;

    public ResetPasswordPanel(final IModel<User> model) {
        super(model);

        final WebMarkupContainer resetContainer = new WebMarkupContainer("resetContainer") {
            @Override
            public boolean isVisible() {
                return !showFeedbackContainer;
            }
        };
        add(resetContainer);
        resetContainer.add(new AjaxLink("resetButton") {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                userManager.resetPassword(model.getObject(), true);
                showFeedbackContainer = true;
                target.add(ResetPasswordPanel.this);
            }
        });

        final WebMarkupContainer feedbackContainer = new WebMarkupContainer("feedbackContainer") {
            @Override
            public boolean isVisible() {
                return showFeedbackContainer;
            }
        };
        add(feedbackContainer);
        feedbackContainer.add(new Label("emailAddress", model.getObject().getEmail()));
        feedbackContainer.add(new Label("newPassword", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return model.getObject().getInitialPassword();
            }
        }));
        feedbackContainer.add(new AjaxLink("okButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                // hide feedbackContainer
                showFeedbackContainer = false;
                target.add(ResetPasswordPanel.this);
            }
        });
    }
}
