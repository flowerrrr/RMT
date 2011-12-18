package de.flower.rmt.model;

/**
 * @author flowerrrr
 */
public enum RSVPStatus {

    ACCEPTED,
    DECLINED,
    UNSURE,
    NORESPONSE;

    public String getResourceKey() {
        return "invitation.status." + this.name().toLowerCase();
    }

    public static RSVPStatus[] quickResponseValues() {
        return new RSVPStatus[]{
                ACCEPTED,
                UNSURE,
                DECLINED
        };
    }
}
