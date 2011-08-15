package de.flower.rmt.service.security;

import de.flower.rmt.model.Role;
import de.flower.rmt.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oblume
 */
public class UserDetailsBean extends User {

    private Users user;

    public UserDetailsBean(Users user) {
        super(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, getGrantedAuthorities(user));
        this.user = user;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(Users user) {
        List<String> grantedAuthorities = new ArrayList<String>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(role.getAuthority());
        }
        return AuthorityUtils.createAuthorityList(grantedAuthorities.toArray(new String[]{}));
    }

    public Users getUser() {
        return user;
    }
}
