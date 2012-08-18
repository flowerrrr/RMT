package de.flower.rmt.service;

import com.google.common.base.Predicate;
import de.flower.common.util.Check;
import de.flower.common.util.NameFinder;
import de.flower.rmt.model.db.entity.Opponent;
import de.flower.rmt.model.db.entity.Opponent_;
import de.flower.rmt.repository.IOpponentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.flower.rmt.repository.Specs.asc;
import static org.springframework.data.jpa.domain.Specifications.where;

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
        return opponentRepo.findAll(where(asc(Opponent_.name)));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        Opponent entity = loadById(id);
        entity.setName(NameFinder.delete(entity.getName(), new Predicate<String>() {
            @Override
            public boolean apply(final String name) {
                return opponentRepo.findByName(name) == null;
            }
        }));
        opponentRepo.softDelete(entity);
    }

    @Override
    public Opponent newInstance() {
        return new Opponent(getClub());
    }
}
