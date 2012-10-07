package de.flower.common.ui.panel

import org.testng.annotations.Test

import static org.testng.Assert.assertEquals

/**
 *
 * @author flowerrrr
 */
@groovy.transform.TypeChecked
class BasePanelTest {

    @Test
    def void testDefaultId() {
        def panel = new TestPanel()
        assertEquals(panel.getId(), "testPanel")
    }
}

class TestPanel extends BasePanel {
}