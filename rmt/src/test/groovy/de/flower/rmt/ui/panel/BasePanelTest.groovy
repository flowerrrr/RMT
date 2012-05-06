package de.flower.rmt.ui.panel

import de.flower.common.ui.panel.BasePanel
import org.testng.annotations.Test
import static org.testng.Assert.assertEquals

/**
 *
 * @author flowerrrr
 */
class BasePanelTest {

    @Test
    def void testDefaultId() {
        def panel = new TestPanel()
        assertEquals(panel.getId(), "testPanel")
    }
}

class TestPanel extends BasePanel {
}