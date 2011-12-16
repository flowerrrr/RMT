package de.flower.rmt.ui.manager.page.players;

import de.flower.rmt.ui.manager.ManagerBasePage;

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
        return "players";
    }

}
