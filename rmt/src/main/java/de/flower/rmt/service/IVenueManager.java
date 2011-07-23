package de.flower.rmt.service;

import de.flower.rmt.model.Venue;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author oblume
 */
public interface IVenueManager {

    void save(Venue venue);

    void delete(Venue venue);

    List<Venue> findAll(Attribute... attributes);

    Venue newVenueInstance();
}
