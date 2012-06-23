package de.flower.rmt.ui.page.users.manager;

import de.flower.rmt.ui.page.base.NavigationPanel;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;

/**
 * @author flowerrrr
 */
public class PlayersPage extends ManagerBasePage {

    public PlayersPage() {

        setHeading("manager.players.heading", null);
        addMainPanel(new PlayerListPanel());
        addSecondaryPanel(new PlayersSecondaryPanel());
    }


    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.USERS;
    }

}
