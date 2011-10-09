package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.event.Event;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IPlayerManager {

    List<Player> findNotResponded(Event event);

}
