package de.flower.common.util.geo;

/**
 * @author oblume
 */
public class Area {

    public double top;
    public double right;
    public double bottom;
    public double left;

    public LatLng getCenter() {
        return new LatLng((top + bottom) / 2, (right + left) / 2);
    }
}
