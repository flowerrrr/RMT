package de.flower.common.test.wicket;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for wicket test that need @SpringBean to work.
 * Wicket components will get mockito mocks injected whenever
 * a @SpringBean annotation is evaluated.
 *
 *  @SpringBean can also be used in the test classes.
 */
public abstract class AbstractWicketMockitoTests {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected WicketTester wicketTester;

    protected MockitoFactoryApplicationContext mockCtx;

    @BeforeMethod
    public void init() {
        mockCtx = new MockitoFactoryApplicationContext();
        mockCtx.setVerboseLogging(isMockitoVerboseLogging());

        wicketTester = createWicketTester(mockCtx);
        WebApplication webApp = wicketTester.getApplication();
        SpringComponentInjector injector = new SpringComponentInjector(webApp, mockCtx);
        webApp.getComponentInstantiationListeners().add(injector);

        // support for @SpringBean and inject mock beans into test classes.
        // need pass paramater wrapInProxy 'false', cause mockito complains about proxied mocks (SpringComponentInjector wraps
        // a proxy around the inject bean by default).
        SpringComponentInjector testClassInjector = new SpringComponentInjector(webApp, mockCtx, false);
        SpringComponentInjectorUtils.resetAllBeans(this, testClassInjector);
        testClassInjector.inject(this);
    }

    protected boolean isMockitoVerboseLogging() {
        return false;
    }

    protected WicketTester createWicketTester(final MockitoFactoryApplicationContext mockCtx) {
        return new WicketTester();
    }

    @AfterMethod
    public void cleanup() {
        Mockito.validateMockitoUsage();
        // must reset mocks to simulate destruction of app context. required when assigning mocks to class fields.
        // wickets SpringComponentInjector does not reset injected fields between test methods.
        resetMocks(mockCtx);
    }

    public static void resetMocks(MockitoFactoryApplicationContext context) {
        MockUtil mockUtil = new MockUtil();
        for (String name : context.getBeans().keySet()) {
            Object bean = context.getBean(name);
            if (mockUtil.isMock(bean)) {
                Mockito.reset(bean);
            }
        }
    }
}
