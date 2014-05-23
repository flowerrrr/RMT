package de.flower.rmt.service;

import static de.flower.rmt.repository.Specs.asc;
import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.List;

import javax.persistence.metamodel.Attribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.model.db.entity.Venue_;
import de.flower.rmt.repository.IVenueRepo;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class VenueManager extends AbstractService implements IVenueManager {

    @Autowired
    private IVenueRepo venueRepo;

    @Override
    @Transactional(readOnly = false)
    public void save(Venue venue) {
        validate(venue);
        venueRepo.save(venue);
    }

    @Override
    public Venue loadById(Long id) {
        return Check.notNull(venueRepo.findOne(id), "Entity [" + id + "] not found");
    }

    @Override
    public List<Venue> findAll(final Attribute... attributes) {
        List<Venue> list = venueRepo.findAll(where(asc(Venue_.name)));
        return list;
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        Venue entity = loadById(id);
        // TODO (flowerrrr - 23.05.2014) fails if name is close to upper size limit. should use NameFinder and then truncate candidate to 80 characters
        entity.setName("DELETED-" + entity.getName());
        venueRepo.softDelete(entity);
    }

    @Override
    public Venue newInstance() {

        Venue venue = new Venue(getClub());
        return venue;
    }
}
