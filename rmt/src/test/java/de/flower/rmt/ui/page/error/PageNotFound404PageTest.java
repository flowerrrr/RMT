package de.flower.rmt.ui.page.error;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.testng.annotations.Test;


public class PageNotFound404PageTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        wicketTester.startPage(PageNotFound404Page.class);
        wicketTester.dumpPage();
    }
}
