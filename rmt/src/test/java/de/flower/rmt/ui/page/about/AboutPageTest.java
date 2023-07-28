package de.flower.rmt.ui.page.about;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.testng.annotations.Test;


public class AboutPageTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new AboutPage());
        wicketTester.dumpPage();
    }
}
