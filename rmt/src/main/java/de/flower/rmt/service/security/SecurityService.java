package de.flower.rmt.service.security;

import de.flower.rmt.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;

/**
 * @author oblume
 */
@Service
public class SecurityService implements ISecurityService {

    @Autowired
    private SecurityContextHolderStrategy schs;

    @Override
    public Users getCurrentUser() {
        Object o = schs.getContext().getAuthentication().getPrincipal();
        if (o instanceof UserDetailsBean) {
            UserDetailsBean principal = (UserDetailsBean) o;
            return principal.getUser();
        } else {
            // anonymous user.
            return null;
        }
    }

    @Override
    public boolean isCurrentUser(Users user) {
        return getCurrentUser().equals(user);
    }
}
