package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Venue;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IVenueManager {

    void save(Venue venue);

    void delete(Long id);

    Venue loadById(Long id);

    List<Venue> findAll(Attribute... attributes);

    Venue newInstance();

}
