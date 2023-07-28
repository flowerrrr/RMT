package de.flower.common.util.geo;

import java.util.List;


public class GeoUtil {

    public static LatLng centerOf(List<LatLng> latLngs) {

        Area bounds = new Area();
        bounds.top = -90; bounds.bottom = 90; bounds.left = 180; bounds.right = -180;
        for (LatLng latLng : latLngs) {
            bounds.top = Math.max(bounds.top, latLng.getLat());
            bounds.bottom = Math.min(bounds.bottom, latLng.getLat());
            bounds.left = Math.min(bounds.left, latLng.getLng());
            bounds.right = Math.max(bounds.right, latLng.getLng());
        }

        return bounds.getCenter();
    }
}
