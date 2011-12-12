package de.flower.common.test.wicket;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for wicket test that need @SpringBean to work.
 * Test subclassing this class will get mockito mocks injected whenever
 * a @SpringBean annotation is evaluated.
 *
 * @author flowerrrr
 */
public abstract class AbstractWicketMockitoTests {

    protected WicketTester wicketTester = null;

    protected MockitoFactoryApplicationContext mockCtx;

    @BeforeMethod
    public void init() {
        mockCtx = new MockitoFactoryApplicationContext();
        wicketTester = createWicketTester(mockCtx);
        WebApplication webApp = wicketTester.getApplication();
        webApp.getComponentInstantiationListeners().add(new SpringComponentInjector(webApp, mockCtx));

    }

    protected WicketTester createWicketTester(final ApplicationContext mockCtx) {
        return new WicketTester();
    }

    @AfterMethod
    public void cleanup() {
        Mockito.validateMockitoUsage();
    }

    public abstract void testRender();
}
