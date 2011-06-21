package de.flower.test;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.util.tester.WicketTesterHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.mockito.Mockito.validateMockitoUsage;

/**
 * Base class for testing wicket components using TestNG.
 */
@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class WicketUnitTestBase extends AbstractTestNGSpringContextTests {

    protected WicketTester wicketTester = null;

    public WicketUnitTestBase() {
        super();
    }

    @BeforeMethod
    public void init() {
        createTester(applicationContext);
    }

    @AfterMethod
    public void cleanup() {
        validateMockitoUsage();
    }

    protected abstract WebApplication createWebApp(ApplicationContext ctx, boolean flag);

    private void createTester(final ApplicationContext ctx) {
        final WebApplication webApp = createWebApp(ctx, true);
        wicketTester = new WicketTester(webApp);
    }

    /**
     * Get the text document that was written as part of this response.
     *
     * @return html markup
     */
    protected String getPageDump() {
        return wicketTester.getLastResponse().getDocument();
    }

    /**
     * Gets the debug component trees. Same as {@link WicketTester#debugComponentTrees()}, only does not log output,
     * rather returns it as a string.
     *
     * @return the debug component trees
     */
    protected String getDebugComponentTrees() {
        return getDebugComponentTrees("");
    }

    protected String getDebugComponentTrees(final String filter) {
        final StringBuilder s = new StringBuilder();
        for (final WicketTesterHelper.ComponentData obj : WicketTesterHelper.getComponentData(wicketTester
                .getLastRenderedPage())) {
            if (obj.path.matches(".*" + filter + ".*")) {
                s.append("path\t").append(obj.path).append(" \t").append(obj.type).append(" \t[").append(obj.value)
                .append("]\n");
            }
        }
        return s.toString();
    }


 }