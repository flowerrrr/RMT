package de.flower.rmt.service;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public interface IEventManager {

    void save(Event entity);

    Event findById(Long id, Attribute... attributes);

    List<Event> findAll();

    void delete(Event entity);

    Event newInstance(EventType eventType);

}
