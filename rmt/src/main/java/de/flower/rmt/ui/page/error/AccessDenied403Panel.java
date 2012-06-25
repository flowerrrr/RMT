package de.flower.rmt.ui.page.error;

import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.util.LoggingUtils;
import de.flower.rmt.ui.app.Links;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class AccessDenied403Panel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(AccessDenied403Panel.class);

    public AccessDenied403Panel() {
        add(Links.contextRoot("home"));
    }
    
    @Override
    protected void onBeforeRender() {
        log.warn("Access denied [{}]", LoggingUtils.toString(RequestCycle.get().getRequest()));
        super.onBeforeRender();
    }
    
}
