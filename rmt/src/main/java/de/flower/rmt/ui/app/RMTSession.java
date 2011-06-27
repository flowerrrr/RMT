package de.flower.rmt.ui.app;

import de.flower.rmt.model.Users;
import de.flower.rmt.service.IUserManager;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

/**
 * @author oblume
 */
public class RMTSession extends WebSession {

    private Users user;

    @SpringBean
    private SecurityContextHolderStrategy schs;

    @SpringBean
    private IUserManager userManager;


    public RMTSession(Request request) {
        super(request);
        Injector.get().inject(this);
        initUser();
    }

    public static RMTSession get() {
        return (RMTSession) WebSession.get();
    }

    private void initUser() {
        if (user == null) {
            user = getCurrentUser();
        }
    }

    public Users getCurrentUser() {
        String username = schs.getContext().getAuthentication().getName();
        Users user = userManager.findByUsername(username);
        return user;
    }



    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
