package de.flower.rmt.service;

import de.flower.common.service.security.IPasswordGenerator;
import de.flower.common.util.Check;
import de.flower.rmt.model.Role;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.model.User_;
import de.flower.rmt.model.type.Password;
import de.flower.rmt.repository.IRoleRepo;
import de.flower.rmt.repository.IUserRepo;
import de.flower.rmt.repository.Specs;
import de.flower.rmt.service.mail.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import javax.validation.Validator;
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

    @Autowired
    private IRoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IPasswordGenerator passwordGenerator;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private Validator validator;

    @Override
    public User loadById(Long id) {
        return Check.notNull(userRepo.findOne(id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByEmail(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(User user) {
        // check that a role is assigned
        if (user.isNew()) {
            notEmpty(user.getRoles(), "user has no role(s) assigned");
        } else {
            userRepo.reattach(user);
        }
        userRepo.save(user);
        for (Role role : user.getRoles()) {
            roleRepo.save(role);
        }
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
    public User newInstance() {

        User user = new User(getClub());
        Role role = new Role(Role.Roles.PLAYER.getRoleName());
        user.getRoles().add(role);
        role.setUser(user);
        initPassword(user);
        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public void resetPassword(final User user, final boolean sendMail) {
        initPassword(user);
        if (sendMail) {
            notificationService.sendResetPasswordMail(user);
        }
        userRepo.save(user);
    }

    private void initPassword(User user) {
        String initialPassword = passwordGenerator.generatePassword();
        user.setInitialPassword(initialPassword);
        user.setEncryptedPassword(encodePassword(initialPassword));
    }

    @Override
    public void updatePassword(final Long userId, Password password) {
        Check.notNull(userId);
        User user = loadById(userId);
        validate(password);
        user.setInitialPassword(null);
        user.setEncryptedPassword(encodePassword(password.getNewPassword()));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encodePassword(password, null);
    }

    @Override
    @Transactional(readOnly = false)
    public void sendInvitation(Long userId) {
        User user = loadById(userId);
        notificationService.sendInvitationNewUser(user, securityService.getCurrentUser());
        user.setInvitationSent(true);
        userRepo.save(user);
    }
}
