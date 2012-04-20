package de.flower.rmt.ui.panel.activityfeed;

import de.flower.rmt.service.IActivityManager;
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
    private IActivityManager activityManager;

    @BeforeMethod
    public void setUp() {
        int num = 10;
        when(activityManager.findLastN(num)).thenReturn(testData.newActivities(num));
    }

    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new ActivityFeedPanel());
        wicketTester.dumpComponentWithPage();
    }

}
