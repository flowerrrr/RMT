package de.flower.rmt.service.geocoding;

import de.flower.common.util.geo.LatLng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class GeocodingServiceTest {

    private final static Logger log = LoggerFactory.getLogger(GeocodingServiceTest.class);

    /**
     * Bean is self contained, can be instantiated and tested.
     */
    private GoogleGeocodingService geocodingService = new GoogleGeocodingService();

    /**
     * Test geht nicht mehr. Verwendung der Google-Maps API muss überarbeitet werden.
     */
    @Test(enabled = false)
    public void testGeocoding() {
        List<GeocodingResult> results = geocodingService.geocode("Werner-Heisenberg-Allee 25\n 80939 München", "de", "de");
        log.info(results.toString());
        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getLatLng(), new LatLng(48.2187997, 11.6247047));
    }
}