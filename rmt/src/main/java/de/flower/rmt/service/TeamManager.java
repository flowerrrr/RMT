package de.flower.rmt.service;

import com.google.common.base.Predicate;
import de.flower.common.util.Check;
import de.flower.common.util.NameFinder;
import de.flower.rmt.model.Player_;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.Team_;
import de.flower.rmt.model.User;
import de.flower.rmt.repository.ITeamRepo;
import de.flower.rmt.repository.Specs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class TeamManager extends AbstractService implements ITeamManager {

    @Autowired
    private ITeamRepo teamRepo;

    @Autowired
    private IEventManager eventManager;

    @Autowired
    private IPlayerManager playerManager;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Team entity) {
        validate(entity);
        teamRepo.save(entity);
    }

    @Override
    public Team loadById(Long id) {
        return Check.notNull(teamRepo.findOne(id));
    }

    @Override
    public List<Team> findAll() {
        return teamRepo.findAll();
    }

    @Override
    public List<Team> findAllByUserPlayer(final User user) {
        Specification spec = Specs.joinEq(Team_.players, Player_.user, user);
        return teamRepo.findAll(spec);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        Team entity = loadById(id);
        entity.setName(NameFinder.delete(entity.getName(), new Predicate<String>() {
            @Override
            public boolean apply(final String name) {
                return teamRepo.findByName(name) == null;
            }
        }));
        teamRepo.softDelete(entity);
        // mark all events related to this team also as deleted
        eventManager.deleteByTeam(entity);
        // delete all players of team squad
        playerManager.deleteByTeam(entity);
    }

    @Override
    public Team newInstance() {
        return new Team(getClub());
    }

}
