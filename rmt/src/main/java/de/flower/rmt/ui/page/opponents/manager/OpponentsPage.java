package de.flower.rmt.ui.page.opponents.manager;

import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;


public class OpponentsPage extends ManagerBasePage {

    public OpponentsPage() {
        setHeading("manager.opponents.heading", null);
        addMainPanel(new OpponentListPanel());
        addSecondaryPanel(new OpponentsSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return Pages.OPPONENTS.name();
    }
}