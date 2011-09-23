package de.flower.rmt.service.security;

import de.flower.rmt.model.Role;
import de.flower.rmt.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oblume
 */
public class UserDetailsBean extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserDetailsBean(User user) {
        super(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, getGrantedAuthorities(user));
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
}
