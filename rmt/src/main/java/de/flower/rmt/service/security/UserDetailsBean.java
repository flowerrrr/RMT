package de.flower.rmt.service.security;

import de.flower.rmt.model.db.entity.Role;
import de.flower.rmt.model.db.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class UserDetailsBean extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserDetailsBean(User user) {
        super(user.getUsername(), user.getEncryptedPassword(), user.isEnabled(), true, true, true, getGrantedAuthorities(user));
        this.user = user;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<String> grantedAuthorities = new ArrayList<String>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(role.getAuthority());
        }
        return AuthorityUtils.createAuthorityList(grantedAuthorities.toArray(new String[]{}));
    }

    public User getUser() {
        return user;
    }

    public boolean isManager() {
        return hasRole(Role.Roles.MANAGER.getRoleName());
    }

    private boolean hasRole(final String roleName) {
        return findRole(roleName) != null;
    }

    private Role findRole(final String roleName) {
        for (Role r : user.getRoles()) {
            if (r.getAuthority().equals(roleName)) {
                return r;
            }
        }
        return null;
    }
}
