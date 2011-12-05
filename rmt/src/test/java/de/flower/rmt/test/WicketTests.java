package de.flower.rmt.test;

import de.flower.rmt.ui.app.TestApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * @author flowerrrr
 */

public class WicketTests extends AbstractIntegrationTests {

    protected RMTWicketTester wicketTester = null;

    protected WebApplication createWebApp(ApplicationContext ctx) {
        return new TestApplication(ctx);
    }

    @BeforeMethod
    public void init() {
        createTester(applicationContext);
    }

    @AfterMethod
    public void cleanup() {
        Mockito.validateMockitoUsage();
    }

    private void createTester(ApplicationContext ctx) {
        WebApplication webApp = createWebApp(ctx);
        wicketTester = new RMTWicketTester(webApp);
    }
}