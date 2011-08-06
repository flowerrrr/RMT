package de.flower.rmt.service.geocoding;

import java.util.List;

/**
 * @author oblume
 */
public interface IGeocodingService {

    List<GeocodingResult> geocode(String address);
}
