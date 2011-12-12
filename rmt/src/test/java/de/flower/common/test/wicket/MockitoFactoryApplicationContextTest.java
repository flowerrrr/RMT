package de.flower.common.test.wicket;

import org.testng.annotations.Test;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class MockitoFactoryApplicationContextTest {

    MockitoFactoryApplicationContext mockCtx = new MockitoFactoryApplicationContext();

    @Test
    public void testGetMock() {
        ITestManager testManager = mockCtx.getMock(ITestManager.class);
        assertNotNull(testManager);

        // verify that returned instance is actually a mock.
        Date expected = new Date();
        when(testManager.load()).thenReturn(expected);
        Date actual = (Date) testManager.load();
        assertEquals(actual, expected);
    }

    public static interface ITestManager {

        void save(Object o);

        Object load();
    }
}
