package de.flower.rmt.service;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IPlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PlayerManager extends AbstractService implements IPlayerManager {

    @Autowired
    private IPlayerRepo playerRepo;


    @Override
    @Transactional
    public List<Player> findNotResponded(Event event) {
        return playerRepo.findNotResponded(event);
    }

}
