package de.flower.rmt.service.security;

import de.flower.rmt.security.UserDetailsBean;
import de.flower.rmt.service.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

/**
 * Spring security application listener to track last login of users.
 * @author flowerrrr
 */
public class ApplicationSecurityListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private final static Logger log = LoggerFactory.getLogger(ApplicationSecurityListener.class);

    @Autowired
    private UserManager userManager;

    @Override
    public void onApplicationEvent(final InteractiveAuthenticationSuccessEvent event) {
        log.info(event.toString());

        try {
            UserDetailsBean userDetailsBean = (UserDetailsBean) event.getAuthentication().getPrincipal();
            userManager.onLoginSuccess(userDetailsBean.getUser());
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
        }
        // source = RememberMeAuthenticationToken
        // source = UsernamePasswordAuthToken
        // both principal UserDetailsBean
    }
}
