package de.flower.rmt.ui.site;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import org.testng.annotations.Test;



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