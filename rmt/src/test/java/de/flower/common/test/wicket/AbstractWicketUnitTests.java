package de.flower.common.test.wicket;

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
    public void setUp() {
        wicketTester = new WicketTester();
        wicketTester.getApplication().getResourceSettings().getStringResourceLoaders().add(new MockStringResourceLoader());
    }

}
