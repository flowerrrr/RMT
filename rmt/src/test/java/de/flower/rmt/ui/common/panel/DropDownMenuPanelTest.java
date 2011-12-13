package de.flower.rmt.ui.common.panel;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class DropDownMenuPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        DropDownMenuPanel panel = new DropDownMenuPanel();
        panel.addLink(new ExternalLink("link", "http://flower.de"), "button.edit");
        wicketTester.startComponentInPage(panel);
        wicketTester.dumpComponentWithPage();
    }
}
