package de.flower.rmt.service;

import de.flower.rmt.model.Users;
import de.flower.rmt.model.Users_;
import de.flower.rmt.repository.IUserRepo;
import de.flower.rmt.repository.Specs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author oblume
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserManager extends AbstractService implements IUserManager {

    @Autowired
    private IUserRepo userRepo;

    @Override
    public Users findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public void save(Users user) {
        userRepo.save(user);
    }

    @Override
    public List<Users> findAll(final Attribute... attributes) {
        Specification hasClub = Specs.eq(Users_.club, getClub());
        Specification fetch = Specs.fetch(attributes);
        List<Users> list = userRepo.findAll(Specs.and(hasClub, fetch));
        return list;
    }

    @Override
    public void delete(Users user) {
        throw new UnsupportedOperationException("Feature not implemented!");
    }


}
