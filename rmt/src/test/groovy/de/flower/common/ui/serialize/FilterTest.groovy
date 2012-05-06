package de.flower.common.ui.serialize

import de.flower.rmt.model.RSVPStatus
import org.testng.annotations.Test
import static org.testng.Assert.assertEquals
import static org.testng.Assert.assertTrue

/**
 *
 * @author flowerrrr
 */

class FilterTest {

    @Test
    def void testFilter() {
        def filter = new Filter("\"de\\.flower\\.rmt\\.model\\.[^-]*?\"")
        filter.addExclusion(RSVPStatus.class.getName())
        def matches = filter.matches("<class=\"de.flower.rmt.model.Event\" />")
        assertEquals(matches.size(), 1)
        matches = filter.matches("<class=\"de.flower.rmt.model.RSVPStatus\" />")
        assertTrue(matches.isEmpty())
    }
}