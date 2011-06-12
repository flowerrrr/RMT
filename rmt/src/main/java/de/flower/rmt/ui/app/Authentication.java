package de.flower.rmt.ui.app;

import org.apache.wicket.authorization.strategies.role.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author oblume
 */
public class Authentication {

    public static Roles getRoles() {
        Roles roles = new Roles();
        getRolesIfSignedIn(roles);
        return roles;
    }


    public static void getRolesIfSignedIn(Roles roles) {
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            addRolesFromAuthentication(roles, authentication);

    }

    private static void addRolesFromAuthentication(Roles roles, org.springframework.security.core.Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
    }

}
