package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.Activity;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 */
public interface IActivityRepo extends IRepository<Activity, Long> {

}
