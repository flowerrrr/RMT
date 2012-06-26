package de.flower.common.test.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.injection.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for pure wicket unit tests.
 * Adds a dummy string resource locator so that all resource keys can be resolved.
 *
 * @author flowerrrr
 */
public class AbstractWicketUnitTests {

    public final static Logger log = LoggerFactory.getLogger(AbstractWicketUnitTests.class);

    protected WicketTester wicketTester;

    @BeforeMethod
    public final void _setUp() {
        wicketTester = new WicketTester();
        wicketTester.getApplication().getResourceSettings().getStringResourceLoaders().add(new MockStringResourceLoader());

        // support for Injector.get()
        new MockInjector(wicketTester.getApplication());

    }

    public static class MockInjector extends Injector {

        public MockInjector(final Application app) {
            bind(app);
        }

        @Override
        public void inject(final Object object) {
            log.warn("Injection not working with this injector instance.");
        }
    }

}
