package de.flower.rmt.ui.common.page.error;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class PageNotFound404PageTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        wicketTester.startPage(PageNotFound404Page.class);
        wicketTester.dumpPage();
    }

}
