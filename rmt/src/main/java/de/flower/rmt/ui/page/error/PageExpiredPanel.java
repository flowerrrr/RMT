package de.flower.rmt.ui.page.error;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.ui.app.Links;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class PageExpiredPanel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(PageExpiredPanel.class);

    public PageExpiredPanel() {
        add(Links.contextRoot("home"));
    }

}
