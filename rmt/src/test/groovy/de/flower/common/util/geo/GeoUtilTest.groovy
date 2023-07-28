package de.flower.common.util.geo

import org.testng.annotations.Test
import static org.testng.Assert.assertEquals

@groovy.transform.TypeChecked
class GeoUtilTest {

    @Test
    void testGetCenter() {

        def latLngs = [new LatLng(90,0), new LatLng(-90,0)]
        def center = GeoUtil.centerOf latLngs
        assertEquals(center, new LatLng(0, 0))

        latLngs.add new LatLng(0, 180)
        latLngs.add new LatLng(0, 0)
        center = GeoUtil.centerOf(latLngs)
        assertEquals(center, new LatLng(0, 90))

        assertEquals(GeoUtil.centerOf([new LatLng(48, 11)]), new LatLng(48, 11))
        assertEquals(GeoUtil.centerOf([new LatLng(48, 11), new LatLng(49,12)]), new LatLng(48.5d, 11.5d))
    }

}