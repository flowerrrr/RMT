package de.flower.rmt.repository;

import de.flower.rmt.model.Team;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface ITeamRepo extends IRepository<Team, Long> {

    // @Deprecated // currently only used for testing
    // Team findByNameAndClub(String name, Club club);

    /**
     * Returns also soft-deleted entities.
     * @param name
     * @return
     */
    Team findByName(String name);
}
