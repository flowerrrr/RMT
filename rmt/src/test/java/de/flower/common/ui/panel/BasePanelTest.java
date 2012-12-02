package de.flower.common.ui.panel;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class BasePanelTest extends AbstractWicketUnitTests {

    @Test
    public void testDefaultId() {
        TestPanel panel = new TestPanel();
        Assert.assertEquals(panel.getId(), "testPanel");

        // test anonymous class
        panel = new TestPanel() {
        };
        Assert.assertEquals(panel.getId(), "testPanel");
    }

    /**
     * Verify that anonymous classes do not contribute to the list of
     * css classes.
     */
    @Test
    public void testCssClasses() {
        TestPanel panel = new TestPanel() {
        };
        Assert.assertEquals(panel.getCssClasses(), "test-panel base-panel generic-panel panel");
    }

    static class TestPanel extends BasePanel {

    }
}
