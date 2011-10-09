package de.flower.rmt.repository;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.event.Event;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IPlayerRepoEx {

    List<Player> findNotResponded(Event event);

}
