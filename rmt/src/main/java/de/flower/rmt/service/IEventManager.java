package de.flower.rmt.service;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;

import java.util.List;

/**
 * @author oblume
 */
public interface IEventManager {

    void save(Event entity);

    List<Event> findAll();

    void delete(Event entity);

    Event newInstance(EventType eventType);
}
