package de.flower.rmt.ui.page.error;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.testng.annotations.Test;


public class AccessDenied403PageTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        wicketTester.startPage(AccessDenied403Page.class);
        wicketTester.dumpPage();
    }

}
