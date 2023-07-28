package de.flower.common.collection

import org.testng.annotations.Test
import static org.testng.Assert.*


// @groovy.transform.TypeChecked
class MRUCacheTest {

    final shouldFail = new GroovyTestCase().&shouldFail

    @Test
    def void testCache() {

        shouldFail(IllegalArgumentException) {
            new MRUCache(0)
        }
        def maxSize = 2
        def mru = new MRUCache(maxSize)

        // test that max size is respected
        mru.put("1", "1")
        mru.put("2", "2")
        mru.put("3", "3")
        assertEquals(maxSize, mru.size())

        // test the useOrder is used to determine oldest element
        mru.clear()
        mru.put("1", "1")
        mru.put("2", "2")
        // now get first inserted element thereby making is "younger" than the later inserted element
        mru.get("1")
        // now add third element -> number 2 must be purged
        mru.put("3", "3")
        assertNotNull(mru.get("1"))
        assertNull(mru.get("2"))
    }

}