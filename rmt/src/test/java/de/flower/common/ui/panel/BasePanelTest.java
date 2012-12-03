package de.flower.common.ui.panel;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class BasePanelTest extends AbstractWicketUnitTests {

    @Test
    public void testDefaultId() {
        TestPanel panel = new TestPanel();
        assertEquals(panel.getId(), "testPanel");

        // test anonymous class
        panel = new TestPanel() {
        };
        assertEquals(panel.getId(), "testPanel");

        // sub-sub-class
        class SubTestPanel extends TestPanel {
        };
        assertEquals(new SubTestPanel().getId(), "subTestPanel");
    }

    /**
     * Verify that anonymous classes do not contribute to the list of
     * css classes.
     */
    @Test
    public void testCssClasses() {
        TestPanel panel = new TestPanel() {
        };
        assertEquals(panel.getCssClasses(), "TestPanel BasePanel");
    }

    static class TestPanel extends BasePanel {

    }

}
