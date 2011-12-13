package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Role;
import de.flower.rmt.model.User;
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

    @Transactional(readOnly = false)
    public void addRole(Long userId, String roleName) {
        Check.notNull(userId);
        Check.notNull(roleName);
        User user = userRepo.findOne(userId);
        if (!hasRole(userId, roleName)) {
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
        User user = userRepo.findOne(userId);
        Role role = findRole(userId, roleName);
        if (role != null) {
            roleRepo.delete(role);
            Check.isTrue(user.getRoles().remove(role));
            userRepo.save(user);
        }
    }

    @Override
    public boolean isManager(final User user) {
        return hasRole(user.getId(), Role.Roles.MANAGER.getRoleName());
    }

    public boolean hasRole(Long userId, String roleName) {
        return findRole(userId, roleName) != null;
    }


    private  Role findRole(Long userId, String roleName) {
        User user = userRepo.findOne(userId);
        for (Role r : user.getRoles()) {
            if (r.getAuthority().equals(roleName)) {
                return r;
            }
        }
        return null;
    }



}
