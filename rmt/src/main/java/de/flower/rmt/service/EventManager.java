package de.flower.rmt.service;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.repository.IEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author oblume
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EventManager extends AbstractService implements IEventManager {

    @Autowired
    private IEventRepo eventRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Event entity) {
        eventRepo.save(entity);
    }

    @Override
    public List<Event> findAll() {
        return eventRepo.findAllByClub(getClub());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Event entity) {
        // TODO (oblume - 11.06.11) decide whether to soft or hard delete entity.
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
