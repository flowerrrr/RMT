package de.flower.rmt.ui.manager.page.players;

import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;

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
        return NavigationPanel.PLAYERS;
    }

}
