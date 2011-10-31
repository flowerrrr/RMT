package de.flower.rmt.ui.common.panel;

import de.flower.rmt.service.security.ISecurityService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class LoggedInUserLabel extends Label {
    
    @SpringBean
    private ISecurityService securityService;

    public LoggedInUserLabel(final String id) {
        super(id); 
    }
}
