package de.flower.rmt.service.security;

import de.flower.rmt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;

/**
 * @author flowerrrr
 */
@Service
public class SecurityService implements ISecurityService {

    @Autowired
    private SecurityContextHolderStrategy schs;

    @Override
    public User getCurrentUser() {
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
    public boolean isCurrentUser(User user) {
        return getCurrentUser().equals(user);
    }
}
