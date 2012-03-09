package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Opponent;

import java.util.List;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 *
 * @author flowerrrr
 */
public interface IOpponentRepo extends IRepository<Opponent, Long> {

    List<Opponent> findAllByClub(Club club);

}
