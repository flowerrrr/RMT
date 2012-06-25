package de.flower.rmt.ui.page.error;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class PageExpiredPageTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        wicketTester.startPage(PageExpiredPage.class);
        wicketTester.dumpPage();
    }
}
