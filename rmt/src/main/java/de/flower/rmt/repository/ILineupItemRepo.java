package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Lineup;
import de.flower.rmt.model.db.entity.LineupItem;

/**
 * @author flowerrrr
 */
public interface ILineupItemRepo extends IRepository<LineupItem, Long> {

    LineupItem findByLineupAndInvitation(Lineup lineup, Invitation invitation);
}
