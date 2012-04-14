package de.flower.rmt.ui.common.panel;

import de.flower.rmt.service.IActivityManager;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Panel displays activity feed.
 *
 * @author flowerrrr
 */
public class ActivityFeedPanel extends BasePanel {

    @SpringBean
    private IActivityManager activityManager;

    public ActivityFeedPanel() {



    }

}
