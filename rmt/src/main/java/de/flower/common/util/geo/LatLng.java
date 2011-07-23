package de.flower.common.util.geo;

/**
 * @author oblume
 */
public class LatLng extends wicket.contrib.gmap3.api.LatLng {

    public LatLng(double lat, double lng) {
        super(lat, lng);
        assert (lat >= -90 && lat <= 90) : "latitude [" + lat + "] out of bounce";
        assert (lng >= -180 && lng <= 180) : "longitude [" + lng + "] out of bounce";
    }

}
