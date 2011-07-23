package de.flower.common.util.geo

import org.testng.annotations.Test
import org.testng.Assert._
import scala.collection.JavaConversions._
import de.flower.common.util.geo.LatLng

/**
 * 
 * @author oblume
 */

class GeoUtilTest {

    @Test
    def testGetCenter() {

        var latLngs = List(new LatLng(90,0), new LatLng(-90,0))
        var center = GeoUtil.centerOf(latLngs.toList)
        assertEquals(center, new LatLng(0, 0))

        latLngs ::= new LatLng(0, 180)
        latLngs ::= new LatLng(0, 0)
        center = GeoUtil.centerOf(latLngs.toList)
        assertEquals(center, new LatLng(0, 90))

        assertEquals(GeoUtil.centerOf(List(new LatLng(48, 11))), new LatLng(48, 11))
        assertEquals(GeoUtil.centerOf(List(new LatLng(48, 11), new LatLng(49,12))), new LatLng(48.5, 11.5))
    }

}