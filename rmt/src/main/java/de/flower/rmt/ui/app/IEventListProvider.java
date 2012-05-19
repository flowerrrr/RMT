package de.flower.rmt.ui.app;

import de.flower.rmt.model.db.entity.event.Event;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IEventListProvider {

    List<Event> getManagerNavbarList();

    List<Event> getPlayerNavbarList();

    List<Event> getManagerEventListPanelList();

    List<Event> getPlayerEventListPanelList();
}
