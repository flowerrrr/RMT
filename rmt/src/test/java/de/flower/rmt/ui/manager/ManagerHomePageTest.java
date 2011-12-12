package de.flower.rmt.ui.manager;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class ManagerHomePageTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new ManagerHomePage());
        wicketTester.dumpPage();
    }
}