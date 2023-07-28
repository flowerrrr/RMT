package de.flower.rmt.service;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import de.flower.common.service.security.PasswordGenerator;
import de.flower.common.util.Check;
import de.flower.common.util.NameFinder;
import de.flower.rmt.model.db.entity.Player_;
import de.flower.rmt.model.db.entity.Role;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.User_;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.dto.Password;
import de.flower.rmt.repository.IRoleRepo;
import de.flower.rmt.repository.IUserRepo;
import de.flower.rmt.repository.Specs;
import de.flower.rmt.service.mail.NotificationService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.InternetAddress;
import javax.persistence.metamodel.Attribute;
import javax.validation.Validator;
import java.util.List;

import static de.flower.common.util.Check.*;
import static de.flower.rmt.repository.Specs.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserManager extends AbstractService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IRoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordGenerator passwordGenerator;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private PlayerManager playerManager;

    @Autowired
    private Validator validator;

    public User loadById(Long id, final Attribute... attributes) {
        User entity = userRepo.findOne(where(eq(User_.id, id)).and(fetch(attributes)).and(fetch(User_.club)));
        Check.notNull(entity);
        assertClub(entity);
        return entity;
    }

    public User findByUsername(String username, final Attribute... attributes) {
        User entity = userRepo.findOne(where(eq(User_.email, username)).and(fetch(attributes)));
        return entity;
    }

    @Transactional(readOnly = false)
    public void save(User user) {
        // check that a role is assigned
        boolean isNew = user.isNew();
        if (isNew) {
            notEmpty(user.getRoles(), "user has no role(s) assigned");
        }
        userRepo.save(user);
        if (isNew) {
            for (Role role : user.getRoles()) {
                roleRepo.save(role);
            }
        }
    }

    @Transactional(readOnly = false)
    public void save(final User user, final boolean isManager) {
        validate(user);
        save(user);
        if (isManager) {
            roleManager.addRole(user.getId(), Role.Roles.MANAGER.getRoleName());
        } else {
            roleManager.removeRole(user.getId(), Role.Roles.MANAGER.getRoleName());
        }
    }

    public List<User> findAll(final Attribute... attributes) {
        List<User> list = userRepo.findAll(where(fetch(attributes)).and(asc(User_.fullname)));
        return list;
    }

    public List<User> findAllFetchTeams(final Attribute... attributes) {
        List<User> list = userRepo.findAllFetchTeams(getClub());
        return list;
    }

    public List<User> findAllByTeam(final Team team) {
        Check.notNull(team);
        Specification spec = Specs.joinEq(User_.players, Player_.team, team);
        return userRepo.findAll(where(spec).and(asc(User_.fullname)));
    }

    public List<User> findAllUnassignedPlayers(final Team team) {
        return userRepo.findAllUnassignedPlayers(team, getClub());
    }

    public List<User> findAllUninvitedPlayers(final Event event) {
        return userRepo.findAllUninvitedPlayers(event, getClub());
    }

    public List<User> findAllUninvitedPlayersByTeam(final Event event, final Team team) {
        // get list of uninvited users and list of users of this team and intersect both sets.
        List<User> uninvited = findAllUninvitedPlayers(event);
        List<User> usersByTeam = findAllByTeam(team);
        uninvited.retainAll(usersByTeam);
        return uninvited;
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        User entity = loadById(id);
        // do not allow to delete currently logged in user
        if (securityService.isCurrentUser(entity)) {
            throw new IllegalArgumentException("Cannot delete currently logged in user.");
        }
        entity.setEmail(NameFinder.delete(entity.getEmail(), new Predicate<String>() {
            @Override
            public boolean apply(final String candidate) {
                return userRepo.findByEmail(candidate) == null;
            }
        }));
        entity.setFullname(NameFinder.delete(entity.getFullname(), new Predicate<String>() {
            @Override
            public boolean apply(final String candidate) {
                return userRepo.findByFullname(candidate) == null;
            }
        }));
        userRepo.softDelete(entity);
        // delete user from all teams
        playerManager.removeUserFromAllTeams(entity);

    }

    public User newInstance() {

        User user = new User(getClub());
        Role role = new Role(Role.Roles.PLAYER.getRoleName());
        user.getRoles().add(role);
        role.setUser(user);
        initPassword(user);
        // by default a new user is assumed to be fit.
        user.setStatus(User.Status.FIT);
        user.setEnabled(true);
        return user;
    }

    @Transactional(readOnly = false)
    public void resetPassword(final User user, final boolean sendMail) {
        initPassword(user);
        if (sendMail) {
            notificationService.sendResetPasswordMail(user, securityService.getUser());
            // even if user hasen't received invitation mail yet
            user.setInvitationSent(true);
        }
        userRepo.save(user);
    }

    private void initPassword(User user) {
        String initialPassword = passwordGenerator.generatePassword();
        user.setInitialPassword(initialPassword);
        user.setEncryptedPassword(encodePassword(initialPassword));
    }

    public void updatePassword(final Long userId, Password password) {
        Check.notNull(userId);
        User user = loadById(userId);
        validate(password);
        user.setInitialPassword(null);
        user.setEncryptedPassword(encodePassword(password.getNewPassword()));
        userRepo.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encodePassword(password, null);
    }

    @Transactional(readOnly = false)
    public void sendInvitation(Long userId) {
        User user = loadById(userId);
        notificationService.sendInvitationNewUser(user, securityService.getUser());
        user.setInvitationSent(true);
        userRepo.save(user);
    }

    public List<InternetAddress> getAddressesForfAllUsers() {
        List<User> users = findAll();
        // convert to list of internet addresses
        List<InternetAddress[]> internetAddresses = Lists.transform(users, new Function<User, InternetAddress[]>() {

            @Override
            public InternetAddress[] apply(final User user) {
                return user.getInternetAddresses();
            }
        });
        return de.flower.common.util.Collections.flattenArray(internetAddresses);
    }

    public void onLoginSuccess(final User userIn) {
        User user = loadById(userIn.getId());
        user.setLastLogin(new DateTime());
        userRepo.save(user);
    }
}
