package de.flower.rmt.service;

import de.flower.rmt.dao.IMyTeamDao;
import de.flower.rmt.model.MyTeamBE;
import de.flower.rmt.repository.IMyTeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author oblume
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MyTeamManager implements IMyTeamManager {

    @SuppressWarnings("unused")
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IMyTeamRepo myTeamRepo;


    @Override
    public void save(MyTeamBE entity) {
        myTeamRepo.save(entity);
    }

    @Override
    public List<MyTeamBE> findAll() {
        return myTeamRepo.findAll();
    }
}
