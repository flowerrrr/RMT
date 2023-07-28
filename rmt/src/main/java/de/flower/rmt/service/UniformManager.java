package de.flower.rmt.service;

import com.google.common.base.Predicate;
import de.flower.common.util.Check;
import de.flower.common.util.NameFinder;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.model.db.entity.Uniform_;
import de.flower.rmt.repository.IUniformRepo;
import de.flower.rmt.repository.Specs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UniformManager extends AbstractService {

    @Autowired
    private IUniformRepo uniformRepo;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Uniform entity) {
        validate(entity);
        uniformRepo.save(entity);
    }

    public Uniform loadById(Long id) {
        return Check.notNull(uniformRepo.findOne(id));
    }

    public List<Uniform> findAll() {
        return uniformRepo.findAll();
    }

    public List<Uniform> findAllByTeam(Team team) {
        Check.notNull(team);
        Specification spec = Specs.eq(Uniform_.team, team);
        return uniformRepo.findAll(spec);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        Uniform entity = loadById(id);
        entity.setName(NameFinder.delete(entity.getName(), new Predicate<String>() {
            @Override
            public boolean apply(final String name) {
                return uniformRepo.findByName(name) == null;
            }
        }));
        uniformRepo.softDelete(entity);
    }

    public Uniform newInstance(Team team) {
        return new Uniform(team);
    }

}
