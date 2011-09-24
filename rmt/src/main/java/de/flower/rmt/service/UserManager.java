package de.flower.rmt.service;

import de.flower.rmt.model.Role;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.model.User_;
import de.flower.rmt.repository.IUserRepo;
import de.flower.rmt.repository.Specs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import java.util.List;

import static de.flower.common.util.Check.*;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserManager extends AbstractService implements IUserManager {

    @Autowired
    private IUserRepo userRepo;

    @Override
    public User findByUsername(String username) {
        return userRepo.findByEmail(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(User user) {
        // check that a role is assigned
        notEmpty(user.getRoles(), "user has no role(s) assigned");
        userRepo.save(user);
    }

    @Override
    public List<User> findAll(final Attribute... attributes) {
        Specification hasClub = Specs.eq(User_.club, getClub());
        Specification fetch = Specs.fetch(attributes);
        List<User> list = userRepo.findAll(Specs.and(hasClub, fetch));
        return list;
    }

    @Override
    public List<User> findUnassignedPlayers(final Team team) {
        return userRepo.findUnassignedPlayers(team, getClub());
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(User user) {
        // do not allow to delete currently logged in user
        if (securityService.isCurrentUser(user)) {
            throw new IllegalArgumentException("Cannot delete currently logged in user.");
        }
        userRepo.delete(user);
    }

    @Override
    public User newUserInstance() {

        User user = new User(getClub());
        Role role = new Role(Role.Roles.PLAYER.getRoleName());
        user.getRoles().add(role);
        role.setUser(user);
        return user;
    }
}
