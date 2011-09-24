package de.flower.rmt.service.geocoding;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IGeocodingService {

    List<GeocodingResult> geocode(String address);
}
