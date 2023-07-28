package de.flower.rmt.ui.app;

import de.flower.common.test.wicket.MockitoFactoryApplicationContext;
import de.flower.rmt.ui.site.BookmarkManager;
import de.flower.rmt.ui.site.PageResolver;
import de.flower.rmt.ui.site.PanelProvider;
import org.apache.wicket.devutils.inspector.RenderPerformanceListener;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;



public class TestRMTApplication extends RMTApplication {

    @Autowired
    private ApplicationContext appCtx;

    /**
     * For usage when this class is loaded by spring application context based integration tests.
     */
    public TestRMTApplication() {

    }

    /**
     * Used when unit testing without an spring application context (rather a mocked context is used).
     * <p/>
     * Invokes the super class' default constructor and stores the given application context for looking up and
     * injecting Spring managed beans into Wicket components. This constructor is mainly intended to be used for testing
     * purposes since normally the application context will be determined using the standard Spring way via the servlet
     * context.
     *
     * @param ctx the context
     */
    public TestRMTApplication(final MockitoFactoryApplicationContext ctx) {
        super(new PageResolver(), new BookmarkManager());
        ctx.putBean("panelProvider", new PanelProvider());
        appCtx = ctx;
    }

    @Override
    protected void init() {
        super.init();
        // override default settings. no problem cause we don't worry about layout bugs in unit-tests.
        getMarkupSettings().setStripWicketTags(false);
        // trace rendering time of components.
        getComponentInstantiationListeners().add(new RenderPerformanceListener());

        // required for RMT-464
        // mountPage("foobar", PlayerPage.class);
    }

    @Override
    protected SpringComponentInjector getSpringComponentInjector() {
        return new SpringComponentInjector(this, appCtx);
    }
}