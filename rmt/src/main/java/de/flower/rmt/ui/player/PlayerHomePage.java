package de.flower.rmt.ui.player;

import de.flower.rmt.ui.common.panel.LogoutLink;
import de.flower.rmt.ui.player.page.events.EventsPage;
import org.apache.wicket.RestartResponseException;

/**
 * @author flowerrrr
 */
// TODO (flowerrrr - 23.10.11) if this class is not used remove it
public class PlayerHomePage extends PlayerBasePage {

    public PlayerHomePage() {

        add(new LogoutLink("logoutLink", this.getClass()));
        throw new RestartResponseException(EventsPage.class);

    }

}