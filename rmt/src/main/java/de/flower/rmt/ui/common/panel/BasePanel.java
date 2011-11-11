package de.flower.rmt.ui.common.panel;

import de.flower.common.util.Misc;
import de.flower.common.util.Strings;
import de.flower.rmt.model.User;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class BasePanel<T> extends GenericPanel<T> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @SpringBean
    private ISecurityService securityService;

    public BasePanel() {
        this(null, null);
    }

    public BasePanel(IModel<T> model) {
        this(null, model);
    }

    protected BasePanel(String id) {
        this(id, null);
    }

    private BasePanel(String id, IModel<T> model) {
        super(getId(id), model);
        log.debug("new " + getId());
        setOutputMarkupId(true);
        // always append a css class to the panels
        add(new AttributeAppender("class", Model.of("panel " + getCssClass()), " "));
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

    /**
     * Most panels let the basepanel determine the id. using default id provides a
     * good naming strategy in your code.
     * @param id
     * @return
     */
    protected static String getId(String id) {
        if (id != null) {
            return id;
        } else {
            String className = Misc.getCallingClassStatic().getSimpleName();
            return Strings.uncapitalize(className);
        }
    }

    private String getCssClass() {
        return Strings.camelCaseToHyphen(getClass().getSimpleName());
    }

}
