package de.flower.rmt.ui.app;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.model.event.QEvent;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.security.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author flowerrrr
 */
@Component
public class EventListProvider implements IEventListProvider {

    @Autowired
    private IEventManager eventManager;

    @Autowired
    private IPropertyProvider propertyProvider;

    @Autowired
    private ISecurityService securityService;

    @Override
    public List<Event> getManagerNavbarList() {
        return eventManager.findAll(Event_.team);
    }

    @Override
    public List<Event> getPlayerNavbarList() {
        return eventManager.findAllUpcomingAndLastNByUser(securityService.getUser(), propertyProvider.getEventsNumPast(), QEvent.event.team);
    }

    @Override
    public List<Event> getManagerEventListPanelList() {
        return eventManager.findAll(Event_.team);
    }

    @Override
    public List<Event> getPlayerEventListPanelList() {
        return eventManager.findAllUpcomingAndLastNByUser(securityService.getUser(), propertyProvider.getEventsNumPast(), QEvent.event.team);
    }
}
