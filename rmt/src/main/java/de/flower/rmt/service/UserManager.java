package de.flower.rmt.service;

import de.flower.rmt.model.Role;
import de.flower.rmt.model.Team;
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

import static de.flower.common.util.Check.*;

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
        return userRepo.findByEmail(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Users user) {
        // check that a role is assigned
        notEmpty(user.getRoles(), "user has no role(s) assigned");
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
    public List<Users> findUnassignedPlayers(Team team) {
        Specification hasClub = Specs.eq(Users_.club, getClub());
        Specification notInTeam = Specs.not(Specs.in(Users_.teams, team));
        List<Users> list = userRepo.findAll(Specs.and(hasClub, notInTeam));
        return list;
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Users user) {
        // do not allow to delete currently logged in user
        if (securityService.isCurrentUser(user)) {
            throw new IllegalArgumentException("Cannot delete currently logged in user.");
        }
        userRepo.delete(user);
    }

    @Override
    public Users newPlayerInstance() {

        Users user = new Users(getClub());
        Role role = new Role(Role.Roles.PLAYER.getRoleName());
        user.getRoles().add(role);
        role.setUser(user);
        return user;
    }
}
