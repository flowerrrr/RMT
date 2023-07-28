package de.flower.common.ui.serialize

import de.flower.common.ui.serialize.Filter.MatchType
import org.testng.annotations.Test
import static org.testng.Assert.assertEquals

@groovy.transform.TypeChecked
class FilterTest {

    @Test
    public void testFilter() {
        def filter = new Filter();
        filter.setBlackList(["de.flower.rmt.model.db.entity..*", "org.logger..*"])
        filter.setWhiteList([".*Panel", ".*Behavior", "java.lang..*"])
        assertEquals(filter.matches("de.flower.rmt.model.db.entity.subpacke.Event"), MatchType.BLACKLIST)
        assertEquals(filter.matches("org.wicket.MyPanel"), MatchType.WHITELIST)
        assertEquals(filter.matches("foobar"), MatchType.NOMATCH)
    }
}