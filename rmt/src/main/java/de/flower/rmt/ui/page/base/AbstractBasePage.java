package de.flower.rmt.ui.page.base;

import de.flower.common.ui.modal.ModalDialogWindowPanel;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.service.security.UserDetailsBean;
import de.flower.rmt.ui.app.Resource;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public abstract class AbstractBasePage extends WebPage implements IAjaxIndicatorAware {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @SpringBean
    private ISecurityService securityService;

    private WebMarkupContainer pageContainer;

    public AbstractBasePage() {
        this(null);
    }

    public AbstractBasePage(IModel<?> model) {
        super(model);

        ModalDialogWindowPanel modalDialogWindowPanel = new ModalDialogWindowPanel();
        add(modalDialogWindowPanel);
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        response.renderCSSReference(Resource.bootstrapCssUrl);
        // main.css is a less file and needs special type attribute. cannot use wicket #renderCss..
        response.renderString("<link href=\"" + relative(Resource.mainCssUrl) + "\" rel=\"stylesheet\" type=\"text/less\"/>\n");
        response.renderCSSReference(Resource.ieCssUrl, null, "IE");
        response.renderJavaScriptReference(Resource.jqueryJsUrl);
        response.renderJavaScriptReference(Resource.lessJsUrl);
        response.renderJavaScriptReference(Resource.bootstrapJsUrl);
        super.renderHead(response);
        // script should be rendered at the very end cause it overrides wicket javascript functions.
        response.renderJavaScriptReference(Resource.mainJsUrl);
    }

    /**
     * copied from HeaderResponse#relative
     */
    private String relative(final String url) {
        RequestCycle rc = RequestCycle.get();
        return rc.getUrlRenderer().renderContextRelativeUrl(url);
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
     *
     * @return
     */
    public UserDetailsBean getUserDetails() {
        return securityService.getCurrentUser();
    }

    public boolean isCurrentUserLoggedIn() {
        return securityService.isCurrentUserLoggedIn();
    }

    protected UserModel getUserModel() {
        return new UserModel(securityService.getUser());
    }
}
