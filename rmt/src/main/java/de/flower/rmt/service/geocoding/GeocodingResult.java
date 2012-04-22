package de.flower.rmt.service.geocoding;

import de.flower.common.util.geo.LatLng;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author flowerrrr
 */
public class GeocodingResult implements Serializable {

    private String formatted_address;

    private Geometry geometry;

    public String getAddress() {
        return formatted_address;
    }

    public LatLng getLatLng() {
        return new LatLng(geometry.location.get("lat"), geometry.location.get("lng"));
    }

    public void setFormatted_address(final String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public void setLatLng(LatLng latLng) {
        this.geometry = new Geometry();
        geometry.location = new HashMap<>();
        geometry.location.put("lat", latLng.getLat());
        geometry.location.put("lng", latLng.getLng());
    }

    private static class Geometry implements Serializable {

        public Map<String, Double> location;

    }

}
