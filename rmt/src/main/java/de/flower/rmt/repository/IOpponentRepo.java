package de.flower.rmt.repository;

import de.flower.rmt.model.Opponent;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IOpponentRepo extends IRepository<Opponent, Long> {

    Opponent findByName(String name);
}
