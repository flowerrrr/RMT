package de.flower.rmt.ui.page.error;

import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.util.LoggingUtils;
import de.flower.rmt.ui.app.Links;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PageExpiredPanel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(PageExpiredPanel.class);

    public PageExpiredPanel() {
        add(Links.contextRoot("home"));
    }

    @Override
    protected void onBeforeRender() {
        log.warn("Page Expired [{}]", LoggingUtils.toString(RequestCycle.get().getRequest()));
        super.onBeforeRender();
    }

}
