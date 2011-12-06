package de.flower.rmt.ui.common.page;

import de.flower.rmt.model.User;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public abstract class AbstractBasePage extends WebPage implements IAjaxIndicatorAware {

    @SpringBean
    private ISecurityService securityService;

    public AbstractBasePage() {
        this(null);
    }

    public AbstractBasePage(IModel<?> model) {
        super(model);
    }

    /**
     * Display a ajax loading indicator for every ajax request.
     *
     * @return
     */
    @Override
    public String getAjaxIndicatorMarkupId() {
        return "veil";
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
