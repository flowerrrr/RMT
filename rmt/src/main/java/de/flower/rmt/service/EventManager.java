package de.flower.rmt.service;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.repository.IEventRepo;
import de.flower.rmt.repository.Specs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EventManager extends AbstractService implements IEventManager {

    @Autowired
    private IEventRepo eventRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Event entity) {
        assertClub(entity);
        eventRepo.save(entity);
    }

    @Override
    public Event findById(Long id, final Attribute... attributes) {
        Specification fetch = Specs.fetch(attributes);
        Event entity = eventRepo.findOne(Specs.and(Specs.eq(Event_.id, id), fetch));
        assertClub(entity);
        return entity;
    }

    @Override
    public List<Event> findAll() {
        return eventRepo.findAllByClub(getClub());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Event entity) {
        // TODO (flowerrrr - 11.06.11) decide whether to soft or hard delete entity.
        assertClub(entity);
        eventRepo.delete(entity);
    }

    public Event newInstance(EventType eventType) {
        try {
            Event event = eventType.getClazz().newInstance();
            event.setClub(getClub());
            return event;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
