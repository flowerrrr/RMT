package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.Uniform;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IUniformRepo extends IRepository<Uniform, Long> {

    /**
     * Returns also soft-deleted entities.
     * @param name
     * @return
     */
    Uniform findByName(String name);
}
