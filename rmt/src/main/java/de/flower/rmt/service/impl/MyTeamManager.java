package de.flower.rmt.service.impl;

import de.flower.rmt.dao.IMyTeamDao;
import de.flower.rmt.model.MyTeamBE;
import de.flower.rmt.service.IMyTeamManager;
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
    private IMyTeamDao myTeamDao;

    @Override
    public List<MyTeamBE> loadAll() {
        return myTeamDao.loadAll();
    }
}
