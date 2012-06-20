package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.CalItem;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface ICalItemRepo extends IRepository<CalItem, Long> {

    // List<CalItem> findAllByUser(User user);
}
