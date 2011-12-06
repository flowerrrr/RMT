package de.flower.rmt.ui.manager;

import de.flower.rmt.test.AbstractWicketTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class ManagerHomePageTest extends AbstractWicketTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new ManagerHomePage());
        wicketTester.dumpPage();
    }
}