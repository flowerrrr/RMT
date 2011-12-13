package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Role;
import de.flower.rmt.model.User;
import de.flower.rmt.model.User_;
import de.flower.rmt.repository.IRoleRepo;
import de.flower.rmt.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class RoleManager implements IRoleManager {

    @Autowired
    private IRoleRepo roleRepo;

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IUserManager userManager;

    @Transactional(readOnly = false)
    public void addRole(Long userId, String roleName) {
        Check.notNull(userId);
        Check.notNull(roleName);
        User user = userManager.loadById(userId, User_.roles);
        if (!user.hasRole(roleName)) {
            Role role = new Role(roleName);
            role.setUser(user);
            user.getRoles().add(role);
            userRepo.save(user);
            roleRepo.save(role);
        }
    }

    @Transactional(readOnly = false)
    public void removeRole(Long userId, String roleName) {
        Check.notNull(userId);
        Check.notNull(roleName);
        User user = userManager.loadById(userId, User_.roles);
        Role role = user.findRole(roleName);
        if (role != null) {
            roleRepo.delete(role);
            Check.isTrue(user.getRoles().remove(role));
            userRepo.save(user);
        }
    }



}
