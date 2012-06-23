package de.flower.rmt.ui.page.base;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class NavigationPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        wicketTester.setSerializationCheck(false);
        wicketTester.startComponentInPage(new NavigationPanel(new INavigationPanelAware() {
            @Override
            public String getActiveTopBarItem() {
                return "home";
            }
        }));
        wicketTester.dumpPage();
    }
}