package de.flower.rmt.ui.page.events.player;

import de.flower.rmt.security.ISecurityService;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
import de.flower.rmt.ui.panel.activityfeed.ActivityFeedPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventsPage extends PlayerBasePage {

    @SpringBean
    private ISecurityService securityService;

    public EventsPage() {
        setHeading("player.events.heading");

        final UserModel userModel = new UserModel(securityService.getUser());
        addMainPanel(new EventListPanel(userModel));
        addSecondaryPanel(new ActivityFeedPanel());
    }

    @Override
     public String getActiveTopBarItem() {
         return Pages.EVENTS.name();
     }

}
