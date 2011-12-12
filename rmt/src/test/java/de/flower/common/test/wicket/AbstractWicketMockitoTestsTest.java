package de.flower.common.test.wicket;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class AbstractWicketMockitoTestsTest extends AbstractWicketMockitoTests {

    @Test
    public void testInjection() {
        TestLink link = new TestLink("foo");
        assertNotNull(link.testManager);
    }

    @Override
    public void testRender() {
        ; // only to satisfy compiler
    }

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
