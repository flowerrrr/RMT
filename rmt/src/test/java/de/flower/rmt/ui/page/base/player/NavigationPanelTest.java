package de.flower.rmt.ui.page.base.player;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import org.testng.annotations.Test;



/**
 * @author flowerrrr
 */

public class NavigationPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new NavigationPanel(new INavigationPanelAware() {
            @Override
            public String getActiveTopBarItem() {
                return "home";
            }
        }));
        wicketTester.dumpPage();
    }
}