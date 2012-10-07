package de.flower.rmt.ui.page.error;

import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.util.LoggingUtils;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.app.Links;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class PageNotFound404Panel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(PageNotFound404Panel.class);

    @SpringBean
    private ISecurityService securityService;

    @SpringBean
    private PageNotFoundAutoRedirecter pageNotFoundAutoRedirecter;

    public PageNotFound404Panel() {
        add(Links.contextRoot("home"));
    }

    @Override
    protected void onBeforeRender() {
        pageNotFoundAutoRedirecter.checkAutoRedirect(RequestCycle.get().getRequest());
        log.warn("Page not found [{}]", LoggingUtils.toString(RequestCycle.get().getRequest()));
        log.warn("Userdetails: " + securityService.getCurrentUser());
        super.onBeforeRender();
    }

 }
