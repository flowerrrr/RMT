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
public class PageNotFound404Panel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(PageNotFound404Panel.class);

    public PageNotFound404Panel() {
        add(Links.contextRoot("home"));
    }

    @Override
    protected void onBeforeRender() {
        log.warn("Page not found [{}]", LoggingUtils.toString(RequestCycle.get().getRequest()));
        super.onBeforeRender();
    }

 }
