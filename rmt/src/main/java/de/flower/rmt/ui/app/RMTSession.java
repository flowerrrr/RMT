package de.flower.rmt.ui.app;

import de.flower.rmt.model.Users;
import de.flower.rmt.service.ISecurityService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author oblume
 */
public class RMTSession extends WebSession {

    private Users user;

    @SpringBean
    private ISecurityService securityService;

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
            user = securityService.getCurrentUser();
        }
    }

    public Users getUser() {
        return user;
    }

}
