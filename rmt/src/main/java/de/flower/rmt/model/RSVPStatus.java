package de.flower.rmt.model;

/**
 * @author flowerrrr
 */
public enum RSVPStatus {

    ACCEPTED,
    DECLINED,
    UNSURE;

    public String getResourceKey() {
        return "response.status." + this.name().toLowerCase();
    }
}
