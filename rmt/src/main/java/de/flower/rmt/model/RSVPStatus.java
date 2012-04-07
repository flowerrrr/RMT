package de.flower.rmt.model;

/**
 * @author flowerrrr
 */
public enum RSVPStatus {

    ACCEPTED,
    DECLINED,
    UNSURE,
    NORESPONSE;

    public static String getResourceKey(RSVPStatus status) {
        String prefix = "invitation.status.";
        if (status == null) {
            return prefix + "null";
        } else {
            return prefix + status.name().toLowerCase();
        }
    }

    public static RSVPStatus[] quickResponseValues() {
        return new RSVPStatus[]{
                ACCEPTED,
                UNSURE,
                DECLINED
        };
    }
}
