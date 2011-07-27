package de.flower.common.util.geo;

import wicket.contrib.gmap3.api.LatLng;

/**
 * @author oblume
 */
public class LatLngEx extends LatLng {

    public LatLngEx(double lat, double lng) {
        super(lat, lng);
        assert (lat >= -90 && lat <= 90) : "latitude [" + lat + "] out of bounce";
        assert (lng >= -180 && lng <= 180) : "longitude [" + lng + "] out of bounce";
    }

    public LatLngEx(LatLng latLng) {
        this(latLng.getLat(), latLng.getLng());
    }

}
