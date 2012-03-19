package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Opponent;
import de.flower.rmt.repository.IOpponentRepo;
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
public class OpponentManager extends AbstractService implements IOpponentManager {

    @Autowired
    private IOpponentRepo opponentRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Opponent entity) {
        validate(entity);
        opponentRepo.save(entity);
    }

    @Override
    public Opponent loadById(Long id) {
        return Check.notNull(opponentRepo.findOne(id));
    }

    @Override
    public List<Opponent> findAll() {
        return opponentRepo.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Opponent entity) {
        // TODO (flowerrrr - 11.06.11) decide whether to soft or hard delete entity.
        opponentRepo.delete(entity);
    }

    @Override
    public Opponent newInstance() {
        return new Opponent(getClub());
    }
}
