package de.flower.rmt.ui.page.base;

import de.flower.common.ui.ajax.behavior.test.SeleniumWaitForAjaxSupportBehavior;
import de.flower.common.ui.behavior.UserVoiceBehavior;
import de.flower.common.ui.modal.ModalDialogWindowPanel;
import de.flower.rmt.service.ApplicationService;
import de.flower.rmt.service.IApplicationService;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.app.Resource;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.devutils.debugbar.DebugBar;
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

    private final static Logger log = LoggerFactory.getLogger(AbstractBasePage.class);

    @SpringBean
    private ISecurityService securityService;

    @SpringBean
    private IApplicationService applicationService;

    public AbstractBasePage() {
        this(null);
    }

    public AbstractBasePage(IModel<?> model) {
        super(model);

        // support for selenium tests.
        add(new SeleniumWaitForAjaxSupportBehavior() {
            @Override
            public boolean isEnabled(final Component component) {
                return Application.get().getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT;
            }
        });

        ModalDialogWindowPanel modalDialogWindowPanel = new ModalDialogWindowPanel();
        add(modalDialogWindowPanel);

        // include dummy component to force rendering of css and js references. must be after modalwindow to keep
        // original order (first wicket.js/css, then ours) and overriding
        add(new AbstractBasePageHead("head"));
        add(new RenderCSSBehavior());

        // TODO (flowerrrr - 18.06.12) move to some subclass
        add(new UserVoiceBehavior() {
            @Override
            protected String getToken() {
                // if next lines fail -> no subpage can be rendered including error pages. so better tolerate errors.
                try {
                    if (securityService.isCurrentUserLoggedIn()) {
                        return applicationService.getProperty(ApplicationService.USERVOICE_TOKEN);
                    } else {
                        return null;
                    }
                } catch (RuntimeException e) {
                    log.error("Error in UserVoiceBehavior: " + e.getMessage(), e);
                    return null;
                }
            }
        });

        add(new DebugBar("debugBar"));
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
     * Have resource contribution in separate behavior cause page's renderHead is called at the very last after
     * all child elements have been processed.
     */
    public static class AbstractBasePageHead extends WebMarkupContainer {

        public AbstractBasePageHead(String id) {
            super(id);
        }

        @Override
        public void renderHead(final IHeaderResponse response) {

            response.renderJavaScriptReference(Resource.jqueryJsUrl);
            response.renderJavaScriptReference(Resource.jqueryUiJsUrl);
            // enable drag n drop for ipad
            String includeTouchJs = "if (window.Touch) { document.write('" + String.format(Resource.scriptLink, relative(Resource.touchJsUrl)) + "'); }";
            response.renderJavaScript(includeTouchJs, "touchJs");
            response.renderJavaScriptReference(Resource.bootstrapJsUrl);
            // script should be rendered at the very end cause it overrides wicket javascript functions.
            response.renderJavaScriptReference(Resource.mainJsUrl);
        }
    }

    /**
     * Custom Css should be rendered at end of head. Add this behavior to page -> rendered last.
     */
    public static class RenderCSSBehavior extends Behavior {

        @Override
        public void renderHead(final Component component, final IHeaderResponse response) {
            response.renderCSSReference(Resource.bootstrapCssUrl);
            // main.css is a less file and needs special type attribute. cannot use wicket #renderCss..
            response.renderString(String.format(Resource.lessLink, relative(Resource.mainCssUrl)));

            response.renderCSSReference(Resource.ieCssUrl, null, "IE");
            String includeTouchCss = "if (window.Touch) { document.write('" + String.format(Resource.lessLink, relative(Resource.touchCssUrl)) + "'); }";
            response.renderJavaScript(includeTouchCss, "touchCss");
            // less script must be loaded after css-files.
            response.renderJavaScriptReference(Resource.lessJsUrl);
        }
    }

    /**
     * copied from HeaderResponse#relative
     */
    public static String relative(final String url) {
        RequestCycle rc = RequestCycle.get();
        return rc.getUrlRenderer().renderContextRelativeUrl(url);
    }
}
