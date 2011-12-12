package de.flower.rmt.test;

import de.flower.common.test.wicket.WicketTester;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.type.Password;
import de.flower.rmt.ui.app.TestApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * @author flowerrrr
 */

public abstract class AbstractWicketIntegrationTests extends AbstractIntegrationTests {

    protected WicketTester wicketTester = null;

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
        wicketTester = new WicketTester(webApp);
        wicketTester.getLoggingSerializerFilter().addInclusion("\"de\\.flower\\.rmt\\.model\\.[^-]*?\"");
        wicketTester.getLoggingSerializerFilter().addExclusion(RSVPStatus.class.getName());
        wicketTester.getLoggingSerializerFilter().addExclusion(Password.class.getName());

    }

    public abstract void testRender();
}