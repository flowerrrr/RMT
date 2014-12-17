package de.flower.rmt.ui.app;

import de.flower.common.ui.serialize.ISerializerListener;
import de.flower.common.ui.serialize.SerializerWrapper;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.serialize.ISerializer;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component(value = "wicketApplication")
public class RMTApplication extends WebApplication {

    private final static Logger log = LoggerFactory.getLogger(RMTApplication.class);

    /** Arbitrary status code for page expired pages. Required to detect ajax-error in CalendarPanel. */
    public static final int PAGE_EXPIRED_STATUS_CODE = 666;

    @Autowired
    private ISerializerListener pageSerializationValidatorListener;

    @Autowired
    private IPageResolver pageResolver;

    @Autowired
    private IBookmarkManager bookmarkManager;

    public static RMTApplication get() {
        return (RMTApplication) WebApplication.get();
    }

    public RMTApplication() {
    }

    protected RMTApplication(IPageResolver pageResolver, IBookmarkManager bookmarkManager) {
        this.pageResolver = pageResolver;
        this.bookmarkManager = bookmarkManager;
    }

    @Override
    protected void init() {
        log.info("***********************************************************************************");
        log.info("*** Version " + Version.VERSION);
        log.info("***********************************************************************************");
        super.init();

        Locale.setDefault(Locale.GERMANY);

        // add support for @SpringBean
        getComponentInstantiationListeners().add(getSpringComponentInjector());
        // google maps have problems when wicket tags are rendered in development mode, so strip those tags
        getMarkupSettings().setStripWicketTags(true);
        // don't use <em>link</em> when disabling links.
        getMarkupSettings().setDefaultBeforeDisabledLink(null);
        getMarkupSettings().setDefaultAfterDisabledLink(null);

        // wicket tries to survive session timeout by recreating the original page. but that leads to strange behavior
        // when clicking links as nothing happens (only current page is refreshed).
        getPageSettings().setRecreateMountedPagesAfterExpiry(false);

        if (usesDevelopmentConfig()) {
            // enable debug bar
            getDebugSettings().setDevelopmentUtilitiesEnabled(true);
            getDebugSettings().setOutputComponentPath(true);
            getDebugSettings().setOutputMarkupContainerClassName(true);
            getRequestLoggerSettings().setRequestLoggerEnabled(true);

            // getComponentInstantiationListeners().add(new RenderPerformanceListener());
            initSerializer();
        }

        bookmarkManager.initBookmarkablePages(this);

        initErrorPages();
    }

    /**
     * Adds a listener that analyses and logs the serialized pages to find out
     * if unwanted objects (e.g. domain objects) are serialized.
     */
    private void initSerializer() {
        final ISerializer serializer = getFrameworkSettings().getSerializer();
        SerializerWrapper wrapper = new SerializerWrapper(serializer);
        wrapper.addListener(pageSerializationValidatorListener);
        getFrameworkSettings().setSerializer(wrapper);
    }

    protected SpringComponentInjector getSpringComponentInjector() {
        return new SpringComponentInjector(this);
    }

    private void initErrorPages() {
        // same url as in web.xml
        mountPage("error/404", pageResolver.getPageNotFoundPage());

        mountPage("error/500", pageResolver.getInternalErrorPage());
        getRequestCycleListeners().add(new ExceptionRequestCycleListener());
        getApplicationSettings().setInternalErrorPage(pageResolver.getInternalErrorPage());
        getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);

        // access denied is not handled by wicket. spring security will redirect request to this url
        mountPage("error/403", pageResolver.getAccessDeniedPage());

        // mountPage("error/" + PageExpiredPage.SC, pageResolver.getPageExpiredPage());
        getApplicationSettings().setPageExpiredErrorPage(pageResolver.getPageExpiredPage());
    }

    @Override
    public Class getHomePage() {
        return pageResolver.getHomePage();
    }

    @Override
    public Session newSession(final Request request, final Response response) {
        return new RMTSession(request);
    }

    /**
     * Output to log instead of System.err.
     */
    @Override
    protected void outputDevelopmentModeWarning() {
        log.warn("\n********************************************************************\n"
                + "*** WARNING: Wicket is running in DEVELOPMENT mode.              ***\n"
                + "***                               ^^^^^^^^^^^                    ***\n"
                + "*** Do NOT deploy to your live server(s) without changing this.  ***\n"
                + "*** See Application#getConfigurationType() for more information. ***\n"
                + "********************************************************************\n");
    }

    @Value("${wicket.configurationtype}")
    public void setRuntimeConfigurationType(final RuntimeConfigurationType runtimeConfigurationType) {
        setConfigurationType(runtimeConfigurationType);
    }
}