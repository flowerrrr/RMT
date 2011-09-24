package de.flower.rmt.ui.app;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class Authentication {

    public static boolean hasRole(String role) {
        for (String r : getRolesIfSignedIn()) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getRolesIfSignedIn() {
        List<String> roles = new ArrayList<String>();
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            addRolesFromAuthentication(roles, authentication);
        }
        return roles;
    }

    private static void addRolesFromAuthentication(List<String> roles, org.springframework.security.core.Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
    }

}
