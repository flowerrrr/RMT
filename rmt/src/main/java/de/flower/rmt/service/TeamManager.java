package de.flower.rmt.service;

import com.google.common.base.Predicate;
import de.flower.common.util.Check;
import de.flower.common.util.NameFinder;
import de.flower.rmt.model.db.entity.Player_;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.Team_;
import de.flower.rmt.model.db.entity.User;
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
public class TeamManager extends AbstractService {

    @Autowired
    private ITeamRepo teamRepo;

    @Autowired
    private EventManager eventManager;

    @Autowired
    private PlayerManager playerManager;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Team entity) {
        validate(entity);
        teamRepo.save(entity);
    }

    public Team loadById(Long id) {
        return Check.notNull(teamRepo.findOne(id));
    }

    public List<Team> findAll() {
        return teamRepo.findAll();
    }

    public List<Team> findAllByUserPlayer(final User user) {
        Specification spec = Specs.joinEq(Team_.players, Player_.user, user);
        return teamRepo.findAll(spec);
    }

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

    public Team newInstance() {
        return new Team(getClub());
    }

}
