package de.flower.rmt.service;

import de.flower.rmt.model.Team;
import de.flower.rmt.repository.ITeamRepo;
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
public class TeamManager extends AbstractService implements ITeamManager {

    @Autowired
    private ITeamRepo teamRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Team entity) {
        teamRepo.save(entity);
    }

    @Override
    public List<Team> findAll() {
        return teamRepo.findAllByClub(getClub());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Team entity) {
        // TODO (oblume - 11.06.11) decide whether to soft or hard delete entity.
        teamRepo.delete(entity);
    }

    @Override
    public Team newTeamInstance() {
        return new Team(getClub());
    }


}
