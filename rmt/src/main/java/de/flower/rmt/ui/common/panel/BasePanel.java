package de.flower.rmt.ui.common.panel;

import de.flower.rmt.model.User;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class BasePanel<T> extends GenericPanel<T> {

    @SpringBean
    private ISecurityService securityService;

    public BasePanel(String id) {
        this(id, null);
    }

    public BasePanel(String id, IModel<T> model) {
        super(id, model);
        add(new AttributeAppender("class", Model.of("panel"), " "));
    }

    /**
     * Should be called by a panel that can be dismissed/closed, like in modal windows or panels that should be hidden
     * when editing is finished.
     * @param target
     */
    protected void onClose(final AjaxRequestTarget target) {

    }

    /**
     * Shortcut to get current user from security context.
     * @return
     */
    protected User getUser() {
        return securityService.getCurrentUser();
    }

    protected UserModel getUserModel() {
        return new UserModel(securityService.getCurrentUser());
    }

}
