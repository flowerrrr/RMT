package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author flowerrrr
 */
public interface IClubRepo extends JpaRepository<Club, Long> {
}
