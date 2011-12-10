package de.flower.rmt.ui.manager.page.player;

import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
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

    private boolean success = false;

    public ResetPasswordPanel(final IModel<User> model) {
        super(model);
        add(new AjaxLink("resetButton") {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                userManager.resetPassword(model.getObject(), true);
                success = true;
                target.add(ResetPasswordPanel.this);
            }
        });
        WebMarkupContainer container = new WebMarkupContainer("feedbackContainer") {
            @Override
            public boolean isVisible() {
                return success;
            }
        };
        add(container);
        container.add(new Label("emailAddress", model.getObject().getEmail()));
        container.add(new Label("newPassword", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return model.getObject().getInitialPassword();
            }
        }));

    }
}
