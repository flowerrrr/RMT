package de.flower.rmt.ui.manager.page.opponents;

import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;

/**
 * @author flowerrrr
 */
public class OpponentsPage extends ManagerBasePage {

    public OpponentsPage() {
        setHeading("manager.opponents.heading", null);
        addMainPanel(new OpponentListPanel());
        addSecondaryPanel(new OpponentsSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.OPPONENTS;
    }
}