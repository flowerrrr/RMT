package de.flower.common.util.geo;

import java.util.List;

/**
 * @author flowerrrr
 */
public class GeoUtil {

    public static LatLngEx centerOf(List<LatLngEx> latLngs) {

        Area bounds = new Area();
        bounds.top = -90; bounds.bottom = 90; bounds.left = 180; bounds.right = -180;
        for (LatLngEx latLng : latLngs) {
            bounds.top = Math.max(bounds.top, latLng.getLat());
            bounds.bottom = Math.min(bounds.bottom, latLng.getLat());
            bounds.left = Math.min(bounds.left, latLng.getLng());
            bounds.right = Math.max(bounds.right, latLng.getLng());
        }

        return bounds.getCenter();
    }
}
