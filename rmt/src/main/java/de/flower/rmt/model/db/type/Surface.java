package de.flower.rmt.model.db.type;


public enum Surface {

    /**
     * DO NOT ALTER THESE NAMES. They are stored in database as strings.
     */
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
