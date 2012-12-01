package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.EventTeamPlayer;
import de.flower.rmt.model.db.entity.Invitation;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IEventTeamPlayerRepo extends IRepository<EventTeamPlayer, Long> {

    EventTeamPlayer findByInvitation(Invitation invitation);
}
