package de.flower.common.ui.serialize

import org.testng.annotations.Test
import org.testng.Assert._
import de.flower.rmt.model.RSVPStatus


/**
 * 
 * @author flowerrrr
 */

class FilterTest {

    @Test
    def testFilter() {
        val filter = new Filter("\"de\\.flower\\.rmt\\.model\\.[^-]*?\"")
        filter.addExclusion(classOf[RSVPStatus].getName())
        var matches = filter.matches("<class=\"de.flower.rmt.model.Event\" />")
        assertEquals(matches.size(), 1)
        matches = filter.matches("<class=\"de.flower.rmt.model.RSVPStatus\" />")
        assertTrue(matches.isEmpty())

    }

}