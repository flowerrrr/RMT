package de.flower.rmt.test;

import de.flower.common.test.wicket.WicketTester;
import de.flower.common.ui.serialize.Filter;
import de.flower.rmt.ui.app.TestRMTApplication;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for ui-test that require full application stack down to database.
 */
public abstract class AbstractRMTWicketIntegrationTests extends AbstractRMTIntegrationTests {

    protected WicketTester wicketTester = null;

    @Autowired
    private TestRMTApplication webApp;

    @Autowired
    private Filter filter;

    @BeforeMethod
    public final void init() {
        if (wicketTester == null) {
            createTester(applicationContext);
        }
    }

    @AfterMethod
    public final void cleanup() {
        Mockito.validateMockitoUsage();
    }

    private void createTester(ApplicationContext ctx) {
        wicketTester = new WicketTester(webApp);
        wicketTester.setPageSerializationValidatorFilter(filter);
    }

}