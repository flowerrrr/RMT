package de.flower.common.test.wicket;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class AbstractWicketMockitoTestsTest extends AbstractWicketMockitoTests {

    @SpringBean
    private ITestManager testManager;

    @Test
    public void testInjectionInComponent() {
        TestLink link = new TestLink("foo");
        assertNotNull(link.testManager);
    }

    @Test
    public void testInjectionInTestClass() {
        assertNotNull(testManager);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static interface ITestManager {

        void save(Object o);
    }

    public static class TestLink extends Link {

        @SpringBean
        public ITestManager testManager;

        public TestLink(String id) {
            super(id);
        }

        @Override
        public void onClick() {
            testManager.save(new Object());
        }
    }
}
