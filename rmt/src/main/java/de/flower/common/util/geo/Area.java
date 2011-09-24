package de.flower.common.util.geo;

/**
 * @author flowerrrr
 */
public class Area {

    public double top;
    public double right;
    public double bottom;
    public double left;

    public LatLngEx getCenter() {
        return new LatLngEx((top + bottom) / 2, (right + left) / 2);
    }
}
