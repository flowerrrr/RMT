package de.flower.rmt.ui.player;

import de.flower.rmt.test.AbstractRMTWicketMokitoTests;
import de.flower.rmt.ui.common.page.INavigationPanelAware;
import org.testng.annotations.Test;



/**
 * @author flowerrrr
 */

public class NavigationPanelTest extends AbstractRMTWicketMokitoTests {

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