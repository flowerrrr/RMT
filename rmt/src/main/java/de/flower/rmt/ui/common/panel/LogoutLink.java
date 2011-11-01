package de.flower.rmt.ui.common.panel;

import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.component.IRequestablePage;

/**
* @author flowerrrr
*/
public class LogoutLink extends Panel {

    public LogoutLink(String id) {
        super(id);
        add(new ExternalLink("logoutLink", "/j_spring_security_logout").setContextRelative(true));
    }

}
