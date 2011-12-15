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
}
