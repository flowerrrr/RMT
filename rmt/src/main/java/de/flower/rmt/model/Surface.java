package de.flower.rmt.model;

/**
 * @author flowerrrr
 */
public enum Surface {

    NATURAL_GRASS,
    ARTIFICIAL_GRASS,
    ASH;

    public static String getResourceKey(Surface object) {
        if (object == null) {
            return "surfaces.null";
        } else {
            return "surfaces." + object.name().toLowerCase();
        }
    }

}
