package de.flower.rmt.service;

import de.flower.rmt.model.MyTeamBE;
import de.flower.rmt.repository.IMyTeamRepo;
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
public class MyTeamManager implements IMyTeamManager {

    @Autowired
    private IMyTeamRepo myTeamRepo;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(MyTeamBE entity) {
        myTeamRepo.save(entity);
    }

    @Override
    public List<MyTeamBE> findAll() {
        return myTeamRepo.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(MyTeamBE entity) {
        // TODO (oblume - 11.06.11) decide whether to soft or hard delete entity.
        myTeamRepo.delete(entity);
    }
}
