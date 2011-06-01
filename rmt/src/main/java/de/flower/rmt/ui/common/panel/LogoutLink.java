package de.flower.rmt.ui.common.panel;

import de.flower.rmt.ui.app.WebSession;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.component.IRequestablePage;

/**
* @author oblume
*/
public class LogoutLink extends Link {

    private Class<? extends IRequestablePage> responsePage;

    public LogoutLink(String id, Class<? extends IRequestablePage> responsePage) {
        super(id);
        this.responsePage = responsePage;
    }

    @Override
    public void onClick() {
        org.apache.wicket.authentication.AuthenticatedWebSession session = WebSession.get();
        session.signOut();
        setResponsePage(responsePage);
    }
}
