package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.type.RSVPStatus;

/**
 * @author flowerrrr
 */
public interface IResponseManager {

    Invitation respond(Long eventId, Long userId, RSVPStatus status);

}