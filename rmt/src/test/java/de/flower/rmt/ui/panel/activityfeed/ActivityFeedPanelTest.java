package de.flower.rmt.ui.panel.activityfeed;

import de.flower.rmt.service.ActivityManager;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

/**
 * @author flowerrrr
 */
public class ActivityFeedPanelTest extends AbstractRMTWicketMockitoTests {

    @SpringBean
    private ActivityManager activityManager;

    @BeforeMethod
    public void setUp() {
        int size = 10;
        when(activityManager.findLastN(0, size)).thenReturn(testData.newActivities(size));
    }

    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new ActivityFeedPanel());
        wicketTester.dumpComponentWithPage();
    }

}
