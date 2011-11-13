package de.flower.common.util.geo;

import wicket.contrib.gmap3.api.GLatLng;

import static de.flower.common.util.Check.*;

/**
 * @author flowerrrr
 */
public class LatLng extends GLatLng {

    public LatLng(double lat, double lng) {
        super(lat, lng);
        isTrue(lat >= -90 && lat <= 90, "latitude [" + lat + "] out of bounce");
        isTrue(lng >= -180 && lng <= 180, "longitude [" + lng + "] out of bounce");
    }

    public LatLng(GLatLng latLng) {
        this(latLng.getLat(), latLng.getLng());
    }

}
