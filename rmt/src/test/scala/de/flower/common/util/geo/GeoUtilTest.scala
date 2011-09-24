package de.flower.common.util.geo

import org.testng.annotations.Test
import org.testng.Assert._
import scala.collection.JavaConversions._
/**
 * 
 * @author flowerrrr
 */

class GeoUtilTest {

    @Test
    def testGetCenter() {

        var latLngs = List(new LatLngEx(90,0), new LatLngEx(-90,0))
        var center = GeoUtil.centerOf(latLngs.toList)
        assertEquals(center, new LatLngEx(0, 0))

        latLngs ::= new LatLngEx(0, 180)
        latLngs ::= new LatLngEx(0, 0)
        center = GeoUtil.centerOf(latLngs.toList)
        assertEquals(center, new LatLngEx(0, 90))

        assertEquals(GeoUtil.centerOf(List(new LatLngEx(48, 11))), new LatLngEx(48, 11))
        assertEquals(GeoUtil.centerOf(List(new LatLngEx(48, 11), new LatLngEx(49,12))), new LatLngEx(48.5, 11.5))
    }

}