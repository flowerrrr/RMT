package de.flower.rmt.ui.page.opponents.manager;

import de.flower.rmt.ui.page.base.NavigationPanel;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;

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