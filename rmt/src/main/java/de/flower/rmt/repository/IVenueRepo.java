package de.flower.rmt.repository;

import de.flower.rmt.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author oblume
 */
public interface IVenueRepo extends JpaRepository<Venue, Long>, JpaSpecificationExecutor<Venue> {

}
