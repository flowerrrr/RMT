package de.flower.rmt.service.geocoding

import org.testng.annotations.Test
import de.flower.test.AbstractIntegrationTests
import org.testng.Assert._
import de.flower.common.util.geo.LatLngEx
import org.slf4j.{LoggerFactory, Logger}

/**
 * 
 * @author oblume
 */

class GeocodingServiceTest {

    protected var log: Logger = LoggerFactory.getLogger(this.getClass());

    val geocodingService = new GoogleGeocodingService();

    @Test
    def testGeocoding() {
        val results = geocodingService.geocode("Werner-Heisenberg-Allee 25\n 80939 München", "de", "de")
        log.info(results.toString())
        assertEquals(results.size(), 1)
        assertEquals(results.get(0).getLatLng(), new LatLngEx(48.2186, 11.62362))
    }

}