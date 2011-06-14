package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author oblume
 */
public interface IClubRepo extends JpaRepository<Club, Long> {
}
